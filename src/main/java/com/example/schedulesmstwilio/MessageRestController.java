package com.example.schedulesmstwilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class MessageRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MessageRestController.class);
    private final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String TWILIO_MESSAGING_SERVICE_SID = System.getenv("TWILIO_MESSAGING_SERVICE_SID");
    private final String PHONE_NUMBER = System.getenv("PHONE_NUMBER");

    public MessageRestController() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @PostMapping(value = "/timer")
    @ResponseBody
    public String scheduleSMS() {
        final ZonedDateTime sendTime = ZonedDateTime.now().plus(26, ChronoUnit.MINUTES);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                        TWILIO_MESSAGING_SERVICE_SID,
                        "Hey, it's Mr. Tomato telling you that 25 minutes have passed!")
                .setSendAt(sendTime)
                .setScheduleType(Message.ScheduleType.FIXED)
                .create();
        LOG.info("Message SID is {}", message.getSid());
        return message.getSid() + " scheduled successfully";
    }
}
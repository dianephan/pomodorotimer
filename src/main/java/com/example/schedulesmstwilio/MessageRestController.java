package com.example.schedulesmstwilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class MessageRestController {
    private final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String TWILIO_MESSAGING_SERVICE_SID = System.getenv("TWILIO_MESSAGING_SERVICE_SID");
    private final String PHONE_NUMBER = System.getenv("PHONE_NUMBER");

    @PostMapping (value = "/timer")
    public ResponseEntity<String> scheduleSMS(){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        final ZonedDateTime sendTime = ZonedDateTime.now().plus(25, ChronoUnit.MINUTES);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                TWILIO_MESSAGING_SERVICE_SID,
                "Hey, it's Mr. Tomato telling you that 25 minutes have passed!")
        .setSendAt(sendTime)
        .setScheduleType(Message.ScheduleType.FIXED)
        .create();
        System.out.println(message.getSid());

        return new ResponseEntity<String>(message.getSid() + " sent successfully", HttpStatus.OK);
    }
}
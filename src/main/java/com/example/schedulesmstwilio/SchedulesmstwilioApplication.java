package com.example.schedulesmstwilio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;

@SpringBootApplication
public class SchedulesmstwilioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulesmstwilioApplication.class, args);
        final NgrokClient ngrokClient = new NgrokClient.Builder().build();
        final CreateTunnel createTunnel = new CreateTunnel.Builder()
                .withAddr(6666)
                .build();
        final Tunnel tunnel = ngrokClient.connect(createTunnel);
    }
}

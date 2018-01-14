package com.acsproject.falldetectionalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FallDetectionAlertApplication {

    public static void main(String[] args) {
        SpringApplication.run(FallDetectionAlertApplication.class, args);
    }
}

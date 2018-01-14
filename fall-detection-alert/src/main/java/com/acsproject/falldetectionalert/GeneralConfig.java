package com.acsproject.falldetectionalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class GeneralConfig {

    @Autowired
    private AlertService smsAlertService;

    @Autowired
    private AlertService emailAlertService;

    private Logger log = LoggerFactory.getLogger(FallAlertController.class);

    @Scheduled(fixedRate = 15000)
    public void reportContacts() {
        log.info("SMS Contacts: {}", smsAlertService.getContacts());
        log.info("Email Contacts: {}", emailAlertService.getContacts());
    }

}

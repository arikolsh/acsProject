package com.acsproject;

import antlr.collections.AST;
import com.acsproject.alert.AlertController;
import com.acsproject.alert.AlertService;
import com.acsproject.alert.AlertServiceFactory;
import com.acsproject.contacts.Contact;
import com.acsproject.contacts.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

import static com.acsproject.alert.AlertType.EMAIL;
import static com.acsproject.alert.AlertType.SMS;

@Configuration
public class GeneralConfig {

    @Autowired
    private AlertServiceFactory alertServiceFactory;

    @Autowired
    ContactRepository contactRepository;

    private Logger log = LoggerFactory.getLogger(AlertController.class);
    @Scheduled(fixedRate = 30000)
    public void reportContacts() {
        log.info("SMS Contacts: {}", alertServiceFactory.getService(SMS).getContacts());
        log.info("Email Contacts: {}", alertServiceFactory.getService(EMAIL).getContacts());
    }

}

package com.acsproject.falldetectionalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class FallAlertController {

    private Logger log = LoggerFactory.getLogger(FallAlertController.class);

    @Autowired
    private AlertService smsAlertService;

    @Autowired
    private AlertService emailAlertService;

    private ResponseEntity<String> FAILURE = new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
    private ResponseEntity<String> SUCCESS = new ResponseEntity<>("success", HttpStatus.OK);
    private ResponseEntity<String> EMPTY = new ResponseEntity<>("", HttpStatus.OK);

    @RequestMapping("/")
    public ResponseEntity index() {
        return EMPTY;
    }

    @RequestMapping("/alert")
    public ResponseEntity alertAll() {
        try {
            emailAlertService.alert();
            smsAlertService.alert();
        } catch (Exception e) {
            log.error("error sending alerts", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("/alert/email")
    public ResponseEntity emailAlert() {
        try {
            emailAlertService.alert();
        } catch (Exception e) {
            log.error("error sending email alert", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("/alert/sms")
    public ResponseEntity smsAlert() {
        try {
            smsAlertService.alert();
        } catch (Exception e) {
            log.error("error sending sms alert", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("add/sms/{contact}") //valid contact example: "972507369191"
    public ResponseEntity addSmsContact(@PathVariable("contact") String contact) {
        try {
            log.info("adding contact {} to SMS contacts", contact);
            smsAlertService.addContact(contact);
        } catch (Exception e) {
            log.error("failed to add contact", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("add/email/{contact}")
    public ResponseEntity addEmailContact(@PathVariable("contact") String contact) {
        try {
            log.info("adding contact {} to Email contacts", contact);
            emailAlertService.addContact(contact);
        } catch (Exception e) {
            log.error("failed to add contact", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("remove/email/{contact}")
    public ResponseEntity removeEmailContact(@PathVariable("contact") String contact) {
        log.info("removing contact {} from Email contacts", contact);
        emailAlertService.removeContact(contact);
        return SUCCESS;
    }

    @RequestMapping("remove/sms/{contact}")
    public ResponseEntity removeSmsContact(@PathVariable("contact") String contact) {
        log.info("removing contact {} from SMS contacts", contact);
        smsAlertService.removeContact(contact);
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping("contacts")
    public Map<String, Set<String>> getAllContacts() {
        Map<String, Set<String>> all = new HashMap();
        all.put("email", emailAlertService.getContacts());
        all.put("sms", smsAlertService.getContacts());
        return all;
    }

    @ResponseBody
    @RequestMapping("contacts/email")
    public Set<String> getEmailContacts() {
        return emailAlertService.getContacts();
    }

    @ResponseBody
    @RequestMapping("contacts/sms")
    public Set<String> getSmsContacts() {
        return smsAlertService.getContacts();
    }

}

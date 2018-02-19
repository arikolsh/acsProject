package com.acsproject.falldetectionalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class ContactController {

    public static final ResponseEntity<AlertResponse> FAILURE = new ResponseEntity<>(new AlertResponse("failure"), HttpStatus.INTERNAL_SERVER_ERROR);
    public static final ResponseEntity<AlertResponse> SUCCESS = new ResponseEntity<>(new AlertResponse("success"), HttpStatus.OK);

    private Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private AlertService smsAlertService;

    @Autowired
    private AlertService emailAlertService;

    @CrossOrigin
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

    @CrossOrigin
    @RequestMapping("add/email/{contact:.+}")
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

    @CrossOrigin
    @RequestMapping("remove/email/{contact:.+}")
    public ResponseEntity removeEmailContact(@PathVariable("contact") String contact) {
        log.info("removing contact {} from Email contacts", contact);
        emailAlertService.removeContact(contact);
        return SUCCESS;
    }

    @CrossOrigin
    @RequestMapping("remove/sms/{contact}")
    public ResponseEntity removeSmsContact(@PathVariable("contact") String contact) {
        log.info("removing contact {} from SMS contacts", contact);
        smsAlertService.removeContact(contact);
        return SUCCESS;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("contacts")
    public Map<String, Set<String>> getAllContacts() {
        Map<String, Set<String>> all = new HashMap();
        all.put("email", emailAlertService.getContacts());
        all.put("sms", smsAlertService.getContacts());
        return all;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("contacts/email")
    public Set<String> getEmailContacts() {
        return emailAlertService.getContacts();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("contacts/sms")
    public Set<String> getSmsContacts() {
        return smsAlertService.getContacts();
    }

}

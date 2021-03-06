package com.acsproject.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.acsproject.alert.AlertType.*;

@RestController
public class AlertController {

    public static final ResponseEntity<AlertResponse> FAILURE = new ResponseEntity<>(new AlertResponse("failure"), HttpStatus.INTERNAL_SERVER_ERROR);
    public static final ResponseEntity<AlertResponse> SUCCESS = new ResponseEntity<>(new AlertResponse("success"), HttpStatus.OK);
    public static final ResponseEntity<AlertResponse> EMPTY = new ResponseEntity<>(new AlertResponse(""), HttpStatus.OK);

    private Logger log = LoggerFactory.getLogger(AlertController.class);

    @Autowired
    private AlertServiceFactory alertServiceFactory;

    @RequestMapping("/")
    public ResponseEntity index() {
        return EMPTY;
    }

    @RequestMapping("/alert")
    public ResponseEntity alertAll() {
        try {
            alertServiceFactory.getService(EMAIL).alert();
            alertServiceFactory.getService(SMS).alert();
        } catch (Exception e) {
            log.error("error sending alerts", e);
            return FAILURE;
        }
        return SUCCESS;

    }

    @RequestMapping("/alert/email")
    public ResponseEntity emailAlert() {
        try {
            alertServiceFactory.getService(EMAIL).alert();
        } catch (Exception e) {
            log.error("error sending email alert", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @RequestMapping("/alert/sms")
    public ResponseEntity smsAlert() {
        try {
            alertServiceFactory.getService(SMS).alert();
        } catch (Exception e) {
            log.error("error sending sms alert", e);
            return FAILURE;
        }
        return SUCCESS;
    }
}

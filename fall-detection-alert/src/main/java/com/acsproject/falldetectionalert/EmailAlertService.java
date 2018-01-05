package com.acsproject.falldetectionalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Component
public class EmailAlertService implements AlertService {

    private String ALERT_SUBJECT = "FALL DETECTION ALERT!";
    private String ALERT_BODY = "FALL DETECTION ALERT!";
    private String fromEmail = "falldetectalerter@gmail.com";
    private String fromEmailPassword = "freefalling";
    private Properties emailServerProps;
    private Set<String> contacts;

    private Logger log = LoggerFactory.getLogger(EmailAlertService.class);

    @PostConstruct
    private void init() {
        contacts = new HashSet<>();
        //init gmail server properties
        emailServerProps = new Properties();
        emailServerProps.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        emailServerProps.put("mail.smtp.port", "587"); //TLS Port
        emailServerProps.put("mail.smtp.auth", "true"); //enable authentication
        emailServerProps.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
    }

    public void addContact(String contact) throws AddressException {
        //validate valid email address
        InternetAddress emailAddr = new InternetAddress(contact);
        emailAddr.validate();
        //add email if valid
        contacts.add(contact);
    }

    public Set<String> getContacts() {
        return contacts;
    }

    public void alert() throws Exception {
        if (contacts.isEmpty()) {
            log.info("contact list is empty");
            return;
        }
        log.info("sending alerts to emails: {}", contacts);
        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromEmailPassword);
            }
        };
        Session session = Session.getInstance(emailServerProps, auth);
        for (String toEmail : contacts) {
            sendEmail(session, toEmail, ALERT_SUBJECT, ALERT_BODY);
        }
    }

    private void sendEmail(Session session, String toEmail, String subject, String body) throws Exception {
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply - Fall Detection Alert"));
        msg.setReplyTo(InternetAddress.parse("no_reply@gmail.com", false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        log.info("message is ready");
        Transport.send(msg);

        log.info("email sent successfully!!");
    }

}

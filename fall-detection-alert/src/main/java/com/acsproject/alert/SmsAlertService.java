package com.acsproject.alert;

import com.acsproject.GeneralConfig;
import com.acsproject.contacts.Contact;
import com.acsproject.contacts.ContactRepository;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SmsAlertService implements AlertService {

    // using https://www.nexmo.com/ services
    public static final String NUMBERS_ONLY_REGEX = "^[0-9]*$";
    private String FROM_NUMBER = "NEXMO"; //todo: can buy virtual number
    private String API_KEY = "c171c3d9";
    private String API_SECRET = "97a82d43de5f42f5";
    private String ALERT_MESSAGE = "FALL DETECTED ALERT!";
    private Logger log = LoggerFactory.getLogger(GeneralConfig.class);
    private Set<String> contacts;

    @Autowired
    private ContactRepository contactRepository;

    @PostConstruct
    public void init() {
        contacts = contactRepository.findByType(getType().getName()).stream().map(Contact::getTarget).collect(Collectors.toSet());

    }

    public void addContact(String contact) throws Exception {
        if (!contact.matches(NUMBERS_ONLY_REGEX) && contact.length() < 13 && contact.length() > 6) {
            throw new Exception("invalid number");
        }
        if (contacts.contains(contact)) {
            log.info("contact already added");
            return;
        }
        contacts.add(contact);
        contactRepository.save(new Contact(getType().getName(), contact));
    }

    public void removeContact(String contact) {
        contacts.remove(contact);
        contactRepository.delete(contact);
    }

    public Set<String> getContacts() {
        return contacts;
    }

    @Override
    public AlertType getType() {
        return AlertType.SMS;
    }

    public void alert() throws Exception {
        if (contacts.isEmpty()) {
            log.info("contact list is empty");
            return;
        }
        AuthMethod auth = new TokenAuthMethod(API_KEY, API_SECRET);
        NexmoClient client = new NexmoClient(auth);
        log.info("sending sms from {} to: {}", FROM_NUMBER, contacts);
        for (String toNumber : contacts) {
            sendSMS(client, toNumber);
        }
    }

    private void sendSMS(NexmoClient client, String toNumber) throws IOException, NexmoClientException {
        SmsSubmissionResult[] responses = new SmsSubmissionResult[0];
        responses = client.getSmsClient().submitMessage(new TextMessage(
                FROM_NUMBER,
                toNumber,
                ALERT_MESSAGE));

        for (SmsSubmissionResult response : responses) {
            if (response.getStatus() >= 1) {
                log.error(response.toString());
                throw new NexmoClientException();
            }
            log.info(response.toString());
        }
    }

}

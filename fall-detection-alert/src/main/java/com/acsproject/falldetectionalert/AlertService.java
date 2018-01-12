package com.acsproject.falldetectionalert;

import java.util.Set;

public interface AlertService {

    void alert() throws Exception;

    void addContact(String contact) throws Exception;

    void removeContact(String contact);

    Set<String> getContacts();
}

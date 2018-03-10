package com.acsproject.contacts;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, String> {
    List<Contact> findByType(String type);
}
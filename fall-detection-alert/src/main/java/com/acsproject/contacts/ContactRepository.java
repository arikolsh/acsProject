package com.acsproject.contacts;


import com.acsproject.alert.AlertType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    List<Contact> findByType(AlertType type);
}
package com.acsproject.alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertServiceFactory {

    @Autowired
    private List<AlertService> services;

    private static final Map<AlertType, AlertService> myServiceCache = new HashMap<>();

    @PostConstruct
    public void initMyServiceCache() {
        for(AlertService service : services) {
            myServiceCache.put(service.getType(), service);
        }
    }

    public static AlertService getService(AlertType type) {
        AlertService service = myServiceCache.get(type);
        if(service == null) throw new RuntimeException("Unknown service type: " + type);
        return service;
    }
}
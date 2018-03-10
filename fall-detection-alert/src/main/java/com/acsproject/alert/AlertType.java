package com.acsproject.alert;

public enum AlertType {
    EMAIL("email"),SMS("sms");

    private final String name;

    AlertType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.finalproject.model.exception;

public class NotUniquePhoneException extends RuntimeException {
    private String phone;

    public NotUniquePhoneException(String phone) {
        super("Phone \"" + phone + "\" is already exist");
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}

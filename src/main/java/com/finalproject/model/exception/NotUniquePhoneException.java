package com.finalproject.model.exception;

public class NotUniquePhoneException extends RuntimeException {
    public NotUniquePhoneException(String phone) {
        super("Phone \"" + phone + "\" is already exist");
    }
}

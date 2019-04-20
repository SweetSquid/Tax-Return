package com.finalproject.model.exception;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String email) {
        super("Email \"" + email + "\" is already exist");
    }
}

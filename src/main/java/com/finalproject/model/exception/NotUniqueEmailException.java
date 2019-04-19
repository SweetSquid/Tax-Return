package com.finalproject.model.exception;

public class NotUniqueEmailException extends RuntimeException {
    private String email;

    public NotUniqueEmailException(String email) {
        super("Email \"" + email + "\" is already exist");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}

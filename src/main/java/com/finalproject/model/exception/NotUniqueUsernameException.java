package com.finalproject.model.exception;

public class NotUniqueUsernameException extends RuntimeException {
    private String username;

    public NotUniqueUsernameException(String username) {
        super("Username \"" + username + "\" is already exist");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

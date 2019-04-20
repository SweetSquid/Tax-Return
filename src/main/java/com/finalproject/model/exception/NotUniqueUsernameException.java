package com.finalproject.model.exception;

public class NotUniqueUsernameException extends RuntimeException {
    public NotUniqueUsernameException(String username) {
        super("Username \"" + username + "\" is already exist");
    }
}

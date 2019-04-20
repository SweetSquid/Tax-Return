package com.finalproject.model.exception;

public class NotUniqueIdCodeException extends RuntimeException {
    public NotUniqueIdCodeException(String idCode) {
        super("ID code \"" + idCode + "\" is already exist");
    }
}
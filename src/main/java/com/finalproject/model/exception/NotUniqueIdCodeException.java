package com.finalproject.model.exception;

public class NotUniqueIdCodeException extends RuntimeException {
    private String idCode;

    public NotUniqueIdCodeException(String idCode) {
        super("ID code \"" + idCode + "\" is already exist");
        this.idCode = idCode;
    }

    public String getIdCode() {
        return idCode;
    }
}
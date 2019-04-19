package com.finalproject.model.service.encryption;

public interface PasswordService {
    String createHash(String password);

    boolean checkPassword(String password, String dbHash);
}

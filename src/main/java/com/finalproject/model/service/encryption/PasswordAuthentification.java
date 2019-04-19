package com.finalproject.model.service.encryption;

public class PasswordAuthentification implements PasswordService {
    @Override
    public String createHash(String password) {
        StringBuilder hash = new StringBuilder();
        char[] hashChar = password.toCharArray();
        int[] res = new int[hashChar.length];
        for (int i = 0; i < hashChar.length; i++) {
            hashChar[i] ^= i;
            res[i] = hashChar[i];
            hash.append(res[i]);

        }
        return hash.toString();
    }

    @Override
    public boolean checkPassword(String password, String dbHash) {
        return this.createHash(password).equals(dbHash);
    }
}

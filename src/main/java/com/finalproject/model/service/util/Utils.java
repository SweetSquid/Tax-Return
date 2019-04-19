package com.finalproject.model.service.util;

public class Utils {

    public static boolean isNotNull(String... strings) {
        for (String string : strings) {
            if (string == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber(String number) {
        return number.matches("^([+\\-])?\\d+$");
    }

}

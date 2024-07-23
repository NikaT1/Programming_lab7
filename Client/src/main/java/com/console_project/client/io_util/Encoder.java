package com.console_project.client.io_util;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encoder {
    private static final String SALT = "*&^mVLCf(#";
    @SneakyThrows
    public static String getEncodedPassword(String password) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        return getEncodedString(messageDigest.digest((SALT + password).getBytes(StandardCharsets.UTF_8)));
    }

    private static String getEncodedString(byte[] bytes) {
        StringBuilder password = new StringBuilder();
        for (byte byte1 : bytes) {
            String s = Integer.toHexString(byte1);
            try {
                password.append(s.substring(s.length() - 2));
            } catch (IndexOutOfBoundsException e) {
                password.append("0").append(s);
            }
        }
        return password.toString();
    }
}

package com.example.ebanking.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class GenerateUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String zeroPadding(Long number, int padding) {
        if (number == null)
            return "";
        String format;
        if (padding > 0) {
            format = "%0" + padding + "d";
        } else {
            format = "%0d";
        }
        return String.format(format, number);
    }

    public static String randomVerifyCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static byte[] randomBytes(int length) {
        byte[] nonce = new byte[length];
        SECURE_RANDOM.nextBytes(nonce);
        return nonce;
    }

    public static String randomBytesAsBase64Url(int length) {
        return Base64.getUrlEncoder().encodeToString(randomBytes(length)).replace("=", "");
    }
}

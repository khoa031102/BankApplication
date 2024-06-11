package com.example.ebanking.database.constant;


public final class ValidationConstants {
    public static final String LOGIN_REGEX = "^(?=[a-z0-9._@-]{3,100}$)(?!.*[._@-]{2})[^._@-].*[^._@-]$"; //"^[_.@a-z0-9-]*$";

    public static final int PASSWORD_MIN_LENGTH = 4;//6
    public static final int PASSWORD_MAX_LENGTH = 50;

    public static final int FULL_NAME_MAX_LENGTH = 200;
    public static final int EMAIL_MAX_LENGTH = 200;
}

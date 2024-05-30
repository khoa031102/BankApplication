package com.example.ebanking.application.security;

/**
 * Application constants.
 */
public class SpringConstants {
    public static class Headers {
        public static final String CLIENT_LANGUAGE = "Client-Language";
    }

    public static class Authorities {
        public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";

        public static final int ROLE_SYSTEM_LEVEL = 2;
        public static final int ROLE_ADMIN_LEVEL = 1;
        public static final int ROLE_USER_LEVEL = 0;

        public static String[] getEndpointsAccessAuthorities() {
            return new String[]{ROLE_SYSTEM, ROLE_ADMIN, ROLE_USER};
        }
    }
}

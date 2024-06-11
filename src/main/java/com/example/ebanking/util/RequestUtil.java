package com.example.ebanking.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.util.UriEncoder;

import java.net.URI;
import java.util.UUID;

public final class RequestUtil {
    public static final String HEADER_TOKEN_ID = "Token-Id";
    public static final String HEADER_TOKEN_SECRET = "Token-Secret";

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};


    public static void setResponseCookie(HttpServletResponse response, String key, String value, String domain, long maxAge) {
        Cookie cookie = new Cookie(key, UriEncoder.encode(value));
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Math.toIntExact(maxAge));
        response.addCookie(cookie);
    }

    public static String getHeader(HttpServletRequest request, String key) {
        if (request == null || key == null)
            return null;
        return request.getHeader(key);
    }

    public static UUID getHeaderTokenId(HttpServletRequest request) {
        try {
            String cicServerId = getHeader(request, HEADER_TOKEN_ID);
            return UUID.fromString(cicServerId);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHeaderTokenSecret(HttpServletRequest request) {
        return getHeader(request, HEADER_TOKEN_SECRET);
    }

    public static String getRemoteHost(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String prefer = request.getHeader("referer");
        if (StringUtils.hasText(prefer)) {
            return URI.create(prefer).getHost();
        }
        String origin = request.getHeader("origin");
        if (StringUtils.hasText(origin)) {
            return URI.create(origin).getHost();
        }
        return request.getRemoteHost();
    }

    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}

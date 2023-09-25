package com.abuabied.teamUp.Helpers;


import com.google.common.hash.Hashing;
import org.springframework.http.ResponseCookie;

import java.nio.charset.StandardCharsets;

public abstract class HelperFunctions {
    public static int comparePasswords(String unhashedPassword, String hashedPassword) {
        String newHashedPassword = hashPassword(unhashedPassword);
        return newHashedPassword.compareTo(hashedPassword);
    }

    public static String hashPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public static ResponseCookie createCookieForUser(String username) {
//        Cookie cookie = new Cookie("username", username);
//        cookie.setDomain("localhost");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setMaxAge(28800);

        ResponseCookie springCookie = ResponseCookie.from("username", username)
//                .httpOnly(true)
                .secure(true)
                .path("/").domain("")
                .maxAge(28800)
                .build();

        return springCookie;
    }
}

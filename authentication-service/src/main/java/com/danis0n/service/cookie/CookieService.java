package com.danis0n.service.cookie;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;

@Service
public class CookieService {

    private final String SESSION_NAME = "session";
    private final Integer SESSION_EXPIRE_TIME = 60 * 60 * 24 * 10;

    public Cookie createSessionCookie(String uuid) {
        Cookie cookie = new Cookie(SESSION_NAME, uuid);
        cookie.setMaxAge(SESSION_EXPIRE_TIME);
        cookie.setSecure(true);

        return cookie;
    }

    public Cookie deleteSessionCookie() {
        Cookie cookie = new Cookie(SESSION_NAME, "");
        cookie.setMaxAge(0);
        cookie.setSecure(true);

        return cookie;
    }
}

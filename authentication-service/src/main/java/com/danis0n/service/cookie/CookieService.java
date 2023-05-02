package com.danis0n.service.cookie;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;

import static com.danis0n.config.SessionConfiguration.SESSION_EXPIRE_TIME_IN_SECONDS;
import static com.danis0n.config.SessionConfiguration.SESSION_NAME;


@Service
public class CookieService {

    public Cookie createSessionCookie(String uuid) {
        Cookie cookie = new Cookie(SESSION_NAME, uuid);
        cookie.setMaxAge(SESSION_EXPIRE_TIME_IN_SECONDS);
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

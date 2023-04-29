package com.danis0n.service.auth.authentication;

import com.danis0n.dto.LoginRequestDto;
import com.danis0n.service.auth.AuthService;
import com.danis0n.service.cookie.CookieService;
import com.danis0n.service.session.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService extends AuthService {

    private final AuthenticationManager authenticationManager;
    private final SessionService sessionService;
    private final CookieService cookieService;

    public String login(LoginRequestDto request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String UUID = randomUUID().toString();

        sessionService.save(UUID, request.getUsername());
        Cookie cookie = cookieService.createSessionCookie(UUID);
        response.addCookie(cookie);

        return UUID;
    }

    // TODO: 29.04.2023 fix token extract
    // TODO: 29.04.2023 Поискать, как можно вставить логин без контроллера, чтобы как у авторизации
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (nonNull(authorization)) {

            if (authorization.startsWith(BEARER_PREFIX)) {
                String token = authorization.substring(BEARER_PREFIX.length()).trim();

                if (!token.isBlank()) {
                    Cookie cookie = cookieService.deleteSessionCookie();
                    response.addCookie(cookie);
                    sessionService.delete(token);

                    return true;
                }

            }

        }

        return false;
    }

}

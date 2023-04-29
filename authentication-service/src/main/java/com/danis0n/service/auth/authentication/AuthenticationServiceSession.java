package com.danis0n.service.auth.authentication;

import com.danis0n.dto.LoginRequestDto;
import com.danis0n.service.cookie.CookieService;
import com.danis0n.service.session.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceSession extends AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final SessionService sessionService;
    private final CookieService cookieService;

    @Override
    public String authenticate(LoginRequestDto request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String uuid = randomUUID().toString();

        sessionService.save(uuid, request.getUsername());
        Cookie cookie = cookieService.createSessionCookie(uuid);
        response.addCookie(cookie);

        return uuid;
    }

    @Override
    public Boolean logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        Optional<String> token = extractBearerTokenHeader(request);

        if (token.isPresent()) {

            try {

                sessionService.delete(token.get());
                Cookie cookie = cookieService.deleteSessionCookie();
                response.addCookie(cookie);

            } catch (Exception e) {
                log.error("Error occurred while logout");
                return false;
            }

        }

        return false;
    }


}

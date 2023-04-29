package com.danis0n.service.auth.authentication;

import com.danis0n.dto.LoginRequestDto;
import com.danis0n.service.cookie.CookieService;
import com.danis0n.service.session.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.ForbiddenException;
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

        String UUID = randomUUID().toString();

        sessionService.save(UUID, request.getUsername());
        Cookie cookie = cookieService.createSessionCookie(UUID);
        response.addCookie(cookie);

        return UUID;
    }

    @Override
    public Boolean logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        return extractBearerTokenHeader(request)
                .flatMap(this::deleteSession)
                .flatMap((isDeleted -> deleteCookies(isDeleted, response)))
                .orElseThrow(() -> new ForbiddenException("error while logout"));
    }


    private Optional<Boolean> deleteSession(@NonNull String token) {
        try {
            return Optional.of(
                    sessionService.delete(token)
            );
        } catch (Exception e) {
            log.info("Error while setting session");
            return Optional.empty();
        }
    }

    private Optional<Boolean> deleteCookies(@NonNull Boolean valid, @NonNull HttpServletResponse response) {
        if (!valid) return Optional.of(false);

        try {

            Cookie cookie = cookieService.deleteSessionCookie();
            response.addCookie(cookie);

            return Optional.of(true);

        } catch (Exception e) {
            log.info("Error while deleting cookie");
            return Optional.of(false);
        }
    }

}

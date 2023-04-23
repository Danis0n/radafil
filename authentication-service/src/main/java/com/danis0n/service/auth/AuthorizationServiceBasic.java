package com.danis0n.service.auth;

import com.danis0n.enums.Role;
import com.danis0n.service.http.HttpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceBasic extends AuthorizationService {

    private final PasswordEncoder BCRYPT;
    private final HttpService httpService;

    @Override
    public Optional<Authentication> authenticate(@NonNull HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<Authentication> check(Credentials credentials) {
        try {

            String password = this.BCRYPT.encode("1234");
            String username = "1234";

            if (credentials.getUsername().equals(username)) {

                if (BCRYPT.matches(credentials.getPassword(), password)) {
                    Authentication authentication = createAuthentication(credentials.getUsername(), Role.ADMIN);
                    return Optional.of(authentication);
                }

            }
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to check Basic Auth credentials", e);
            return Optional.empty();
        }
    }

}

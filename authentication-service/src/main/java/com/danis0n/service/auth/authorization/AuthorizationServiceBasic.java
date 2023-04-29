package com.danis0n.service.auth.authorization;

import com.danis0n.client.UserClient;
import com.danis0n.entity.AuthenticationData;
import com.danis0n.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceBasic extends AuthorizationService {

    private final PasswordEncoder BCRYPT;
    private final UserClient userClient;

    @Override
    public Optional<Authentication> authorize(@NonNull HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<Authentication> check(@NonNull Credentials credentials) {
        try {

            AuthenticationData data = userClient
                    .retrieveAdminCredentialsByUsername(credentials.getUsername())
                    .getBody();

            if (!nonNull(data)) return Optional.empty();

            if (credentials.getUsername().equals(data.getUsername())) {

                if (BCRYPT.matches(credentials.getPassword(), BCRYPT.encode(data.getHashedPassword()))) {
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

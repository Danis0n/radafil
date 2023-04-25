package com.danis0n.service.auth;

import com.danis0n.client.AuthClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.danis0n.enums.Role.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceBasic extends AuthorizationService {

    private final AuthClient authClient;
    private final static String URI_ADMIN = "http://localhost:8081/api/v1/auth/admin";

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<AuthorizationResponse> check(final Credentials credentials) {

        try {

            String header = credentials.getUsername() + ":" + credentials.getPassword();
            byte[] encodedAuth = Base64.encodeBase64(
                    header.getBytes(StandardCharsets.US_ASCII)
            );

            String authHeader = BASIC_PREFIX + new String(encodedAuth);

            Optional<Boolean> isAuthorized =
                    authClient.sendRequest(
                            URI_ADMIN,
                            credentials.getUsername(),
                            credentials.getPassword(),
                            BASIC_PREFIX
                    );

            return isAuthorized.isPresent() && isAuthorized.get() == Boolean.TRUE ?
                    Optional.of(new AuthorizationResponse(ADMIN.toString())) : Optional.empty();

        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }

    }

}

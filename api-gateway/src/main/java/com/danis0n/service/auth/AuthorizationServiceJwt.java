package com.danis0n.service.auth;

import com.danis0n.client.AuthClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.danis0n.enums.Role.SYSTEM;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceJwt extends AuthorizationService {

    private final AuthClient authClient;
    private final static String URI_SYSTEM = "http://localhost:8081/api/v1/auth/system";

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<AuthorizationResponse> verify(final String token) {

        try {

            Optional<Boolean> isAuthorized =
                    authClient.sendRequest(URI_SYSTEM, token, BEARER_PREFIX);

            return isAuthorized.isPresent() && isAuthorized.get() == Boolean.TRUE ?
                    Optional.of(new AuthorizationResponse(SYSTEM.toString())) : Optional.empty();

        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }

    }

}

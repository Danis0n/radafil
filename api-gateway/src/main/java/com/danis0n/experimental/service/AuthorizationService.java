package com.danis0n.experimental.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
public abstract class AuthorizationService {

    protected static final String BEARER_PREFIX = "Bearer ";

    public abstract Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request);

    protected static Optional<String> extractBearerTokenHeader(@NonNull ServerHttpRequest request) {
        try {

            String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (nonNull(authorization)) {
                if (authorization.startsWith(BEARER_PREFIX)) {
                    String token = authorization.substring(BEARER_PREFIX.length()).trim();

                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }

                }
            }
            return Optional.empty();

        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract bearer token", e);
            return Optional.empty();
        }
    }

    @Getter
    @Setter
    public static class AuthorizationResponse {

        private String role;

        AuthorizationResponse(String role) {
            this.role = role;
        }
    }

}

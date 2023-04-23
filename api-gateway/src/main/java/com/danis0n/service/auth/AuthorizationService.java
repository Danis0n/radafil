package com.danis0n.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Base64;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;

@Slf4j
public abstract class AuthorizationService {

    protected static final String BEARER_PREFIX = "Bearer ";
    protected static final String BASIC_PREFIX = "Basic ";
    private static final Base64.Decoder B64_DECODER = Base64.getDecoder();

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

    protected static Optional<Credentials> extractBasicAuthHeader(@NonNull ServerHttpRequest request) {
        try {

            String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (nonNull(authorization)) {

                if (authorization.startsWith(BASIC_PREFIX)) {
                    String encodedCredentials = authorization.substring(BASIC_PREFIX.length());
                    String decodedCredentials = new String(B64_DECODER.decode(encodedCredentials), UTF_8);
                    if (decodedCredentials.contains(":")) {
                        String[] split = decodedCredentials.split(":", 2);
                        Credentials credentials = new Credentials(split[0], split[1]);
                        return Optional.of(credentials);
                    }

                }

            }
            return Optional.empty();

        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract basic credentials", e);
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

    @Data
    protected static class Credentials {

        private final String username;
        @ToString.Exclude
        private final String password;

    }

}

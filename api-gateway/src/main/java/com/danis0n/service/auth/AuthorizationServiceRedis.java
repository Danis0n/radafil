package com.danis0n.service.auth;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthorizationServiceRedis extends AuthorizationService {

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::lookup);
    }

    private Optional<AuthorizationResponse> lookup(String token) {
        log.info("TOKEN: {}", token);
//        return Optional.of(new AuthorizationResponse("USER"));
        return Optional.empty();
    }
}

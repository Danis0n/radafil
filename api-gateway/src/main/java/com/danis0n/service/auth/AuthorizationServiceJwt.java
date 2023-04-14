package com.danis0n.service.auth;

import com.danis0n.service.http.HttpService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceJwt extends AuthorizationService {

    private final HttpService httpService;
    private final static String URI_ADMIN = "http://localhost:8081/api/v1/auth/admin";

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<AuthorizationResponse> verify(String token) {

        try {

            Optional<Boolean> isAuthorized =
                    httpService.verify(BEARER_PREFIX + token, URI_ADMIN);

            return isAuthorized.isPresent() && isAuthorized.get() == Boolean.TRUE ?
                    Optional.of(new AuthorizationResponse("ADMIN")) : Optional.empty();

        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }

    }

}

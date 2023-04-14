package com.danis0n.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceJwt extends AuthorizationService {

    private final RestTemplate restTemplate;

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBearerTokenHeader(request).flatMap(this::verify);
    }

    private Optional<AuthorizationResponse> verify(String token) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);

            HttpEntity<Boolean> entity = new HttpEntity<>(headers);

            String uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8081/api/v1/auth/admin")
                    .toUriString();

            HttpEntity<Boolean> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Boolean.class);

            Boolean isAuthorized = response.getBody();

            return isAuthorized == Boolean.TRUE ?
                    Optional.of(new AuthorizationResponse("ADMIN")) : Optional.empty();


        } catch (Exception e) {
            log.error(String.valueOf(e));
            return Optional.empty();
        }

    }

}

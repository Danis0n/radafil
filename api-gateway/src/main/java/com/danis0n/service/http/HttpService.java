package com.danis0n.service.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpService {

    private final RestTemplate restTemplate;

    public Optional<Boolean> verify(@NonNull String authorizationHeader, @NonNull String uri) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

        HttpEntity<Boolean> entity = new HttpEntity<>(headers);

        try {

            Optional<HttpEntity<Boolean>> response = Optional.of(
                    restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            entity,
                            Boolean.class
                    )
            );

            return response.map(HttpEntity::getBody);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

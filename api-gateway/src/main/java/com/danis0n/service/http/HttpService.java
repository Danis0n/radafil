package com.danis0n.service.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpService {

    private final RestTemplate restTemplate;

    public Optional<Boolean> sendRequest(@NonNull final String uri,
                                         @NonNull final String token,
                                         @NonNull final String prefix
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, prefix + token);

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

    public Optional<Boolean> sendRequest(@NonNull final String uri,
                                         @NonNull final String username,
                                         @NonNull final String password,
                                         @NonNull final String prefix
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String credentials = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                credentials.getBytes(StandardCharsets.US_ASCII)
        );

        String authHeader = prefix + new String(encodedAuth);

        headers.set(HttpHeaders.AUTHORIZATION, authHeader);

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

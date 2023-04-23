package com.danis0n.service.auth;

import com.danis0n.service.http.HttpService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.danis0n.enums.Role.ADMIN;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceBasic extends AuthorizationService {

    private final HttpService httpService;
    private final static String URI_ADMIN = "http://localhost:8081/api/v1/auth/admin";

    @Override
    public Optional<AuthorizationResponse> authorize(@NonNull ServerHttpRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<AuthorizationResponse> check(final Credentials credentials) {

        try {

            Optional<Boolean> isAuthorized =
                    httpService.sendRequest(
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

package com.danis0n.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

//    TODO: move to yaml file with openApiEndpoints
    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/v1/user/register",
            "/api/v1/auth/login",
            "/api/v1/user/find-by-name"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_API_ENDPOINTS
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

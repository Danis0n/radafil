package com.danis0n.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

//    TODO: move to yaml file with openApiEndpoints
    public static final List<String> openApiEndpoints = List.of(
//            "/api/v1/auth/system",
//            "/api/v1/auth/user"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

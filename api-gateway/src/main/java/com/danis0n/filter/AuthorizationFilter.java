package com.danis0n.filter;

import com.danis0n.service.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthorizationFilter
        extends AbstractGatewayFilterFactory<AuthorizationFilter.Config>
        implements RoleValidator {

    private final RouteValidator validator;
    private final List<AuthorizationService> authServices;

    public AuthorizationFilter(RouteValidator validator, List<AuthorizationService> authServices) {
        super(Config.class);
        this.validator = validator;
        this.authServices = authServices;
    }

    @RequiredArgsConstructor
    public static class Config {

        private final String role;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                try {

                    Optional<Boolean> isAuthorized = authorize(exchange.getRequest(), config.role);

                    if (isAuthorized.isPresent() && isAuthorized.get() == Boolean.TRUE) {
                        return chain.filter(exchange);
                    }

                } catch (Exception e) {
                    log.error("An error occurred while authorization");
                    ServerHttpResponse response = exchange.getResponse();
                    return handleExceptionBehavior(response);
                }

            }

            ServerHttpResponse response = exchange.getResponse();
            return handleExceptionBehavior(response);
        };
    }

    public Optional<Boolean> authorize(ServerHttpRequest request, String requiredRole) {
        for (AuthorizationService authService : authServices) {
            Optional<AuthorizationService.AuthorizationResponse> isAuthorized = authService.authorize(request);

            if (isAuthorized.isPresent()) {
                return Optional.of(
                        validate(requiredRole, isAuthorized.get().getRole())
                );
            }

        }
        return Optional.empty();
    }

    @Override
    public boolean validate(String requiredRole, String currentRole) {
        return requiredRole.equals(NONE_ROLE) || currentRole.equals(requiredRole);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("role");
    }

    private Mono<Void> handleExceptionBehavior(ServerHttpResponse response) {
        response.setStatusCode(HttpStatusCode.valueOf(HttpStatus.SC_UNAUTHORIZED));
        return response.setComplete();
    }
}

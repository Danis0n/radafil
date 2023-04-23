package com.danis0n.config;

import com.danis0n.service.auth.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final List<AuthorizationService> authServices;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .addFilterAt(this::authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(config -> {
                    config.anyRequest().authenticated();
                })
                .sessionManagement(config -> {
                    config.sessionCreationPolicy(STATELESS);
                })
                .exceptionHandling(conf -> {
                    conf.authenticationEntryPoint(this::authenticationFailedHandler);
                })
                .build();
    }

    private void authenticationFilter(
            ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        Optional<Authentication> authentication = this.authenticate((HttpServletRequest) request);

        authentication.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> authenticate(HttpServletRequest request) {
        for (AuthorizationService authService: this.authServices) {

            Optional<Authentication> authentication = authService.authenticate(request);
            if (authentication.isPresent()) {
                return authentication;
            }
        }
        return Optional.empty();
    }

    private void authenticationFailedHandler(HttpServletRequest request,
                                             HttpServletResponse response,
                                             AuthenticationException authException) {
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}

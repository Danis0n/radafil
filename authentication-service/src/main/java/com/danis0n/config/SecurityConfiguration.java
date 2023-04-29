package com.danis0n.config;

import com.danis0n.client.UserClient;
import com.danis0n.service.authorization.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final List<AuthorizationService> authServices;

    // TODO: 26.04.2023 ADD AUTH-FREE REQUESTS FROM API-GATEWAY : LOCALHOST:8080
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .addFilterAt(this::authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(config -> {
                    config.requestMatchers("api/v1/auth/login").permitAll();
                })
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

    private void authorizationFilter(
            ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        Optional<Authentication> authorization = this.authorize((HttpServletRequest) request);

        authorization.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> authorize(HttpServletRequest request) {
        for (AuthorizationService authService: this.authServices) {

            Optional<Authentication> authentication = authService.authorize(request);
            if (authentication.isPresent()) {
                return authentication;
            }
        }
        return Optional.empty();
    }

    private void authenticationFailedHandler(HttpServletRequest request,
                                             HttpServletResponse response,
                                             AuthenticationException authException) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

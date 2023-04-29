package com.danis0n.config;

import com.danis0n.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration {

    private final String USER_SERVICE_URI = "http://user-service";
    private final LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(USER_SERVICE_URI)
                .filter(filterFunction)
                .build();
    }

    @Bean
    public UserClient userClient() {
        HttpServiceProxyFactory spf =
                HttpServiceProxyFactory
                        .builder(WebClientAdapter.forClient(userWebClient()))
                        .build();
        return spf.createClient(UserClient.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> Optional.ofNullable(userClient().retrieveUserCredentialsByUsername(username).getBody())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}

package com.danis0n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(
//            RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter
//    ) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/api/v1/user/**")
//                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://user-service"))
//                .build();
//    }

}

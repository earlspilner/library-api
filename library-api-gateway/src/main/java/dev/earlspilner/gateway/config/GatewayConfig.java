package dev.earlspilner.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alexander Dudkin
 */
@Configuration
@EnableHystrix
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Autowired
    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USERS-SERVICE"))
                .route("auth-server", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTHENTICATION-SERVER"))
                .route("books-service", r -> r.path("/api/books/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://BOOKS-SERVICE"))
                .route("loan-service", r -> r.path("/api/loans/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://LOANS-SERVICE"))
                .route("library-service", r -> r.path("/api/library/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://LIBRARY-SERVICE"))
                .build();
    }

}

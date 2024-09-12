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

    static final String USERS_SERVICE = "http://localhost:9091";
    static final String AUTH_SERVER = "http://localhost:6969";
    static final String BOOKS_SERVICE = "http://localhost:9092";
    static final String LOAN_SERVICE = "http://localhost:9093";
    static final String LIBRARY_SERVICE = "http://localhost:9094";

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
                        .uri(USERS_SERVICE))
                .route("auth-server", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri(AUTH_SERVER))
                .route("books-service", r -> r.path("/api/books/**")
                        .filters(f -> f.filter(filter))
                        .uri(BOOKS_SERVICE))
                .route("loan-service", r -> r.path("/api/loans/**")
                        .filters(f -> f.filter(filter))
                        .uri(LOAN_SERVICE))
                .route("library-service", r -> r.path("/api/library/**")
                        .filters(f -> f.filter(filter))
                        .uri(LIBRARY_SERVICE))
                .build();
    }

}

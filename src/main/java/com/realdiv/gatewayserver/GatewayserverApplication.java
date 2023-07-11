package com.realdiv.gatewayserver;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        // build customised routing configuration
        return builder.routes()
            .route(p -> p
                .path("/realbank/accounts/**")
                .filters(f -> f
                    .rewritePath("/realbank/accounts/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", new Date().toString())
                )
                .uri("lb://ACCOUNTS")
            )
            .route(p -> p
                .path("/realbank/loans/**")
                .filters(f -> f
                    .rewritePath("/realbank/loans/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", new Date().toString())
                )
                .uri("lb://LOANS")
            )
            .route(p -> p
                .path("/realbank/cards/**")
                .filters(f -> f
                    .rewritePath("/realbank/cards/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", new Date().toString())
                )
                .uri("lb://CARDS")
            )
            .build();
    }

}

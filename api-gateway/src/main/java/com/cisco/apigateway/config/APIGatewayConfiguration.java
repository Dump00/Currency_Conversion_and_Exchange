package com.cisco.apigateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class APIGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

//        Function<PredicateSpec, Buildable<Route>> routeFunction = p -> p.path("/get")
//                .filters(f -> f.addRequestHeader("MyHeader", "MyURI"))
//                .uri("http://httpbin.org:80");

        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://CURRENCY-EXCHANGE"))
                .route(p -> p.path("/currency-conversion-controller/**")
                        .uri("lb://CURRENCY-CONVERSION-SERVICE"))
                .route(p -> p.path("/currency-conversion-controller-feign/**")
                        .uri("lb://CURRENCY-CONVERSION-SERVICE"))
                .route(p -> p.path("/currency-conversion-controller-new/**")
                        .filters(f -> f.rewritePath("/currency-conversion-controller-new/(?<segment>.*)",
                                "/currency-conversion-controller-feign/${segment}"))
                        .uri("lb://CURRENCY-CONVERSION-SERVICE"))
                .build();
    }
}

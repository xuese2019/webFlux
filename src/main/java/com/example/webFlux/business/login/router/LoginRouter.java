package com.example.webFlux.business.login.router;

import com.example.webFlux.business.login.handler.LoginHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoginRouter {

    @Bean
    public RouterFunction<ServerResponse> loginR(LoginHandler loginHandler) {
        return RouterFunctions.nest(
                path("/login"),
                route(
                        POST("/login")
                                .and(accept(MediaType.APPLICATION_JSON))
                        , loginHandler::login
                )
        );
    }
}

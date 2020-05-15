package com.example.webFlux.business.file.router;

import com.example.webFlux.business.file.handler.FileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class FileRouter {
    @Bean
    public RouterFunction<ServerResponse> fileR(FileHandler fileHandler) {
        return RouterFunctions.nest(
                path("/file"),
                route(
                        POST("/file")
                                .and(accept(MediaType.MULTIPART_FORM_DATA))
                        , fileHandler::upload
                )
        );
    }
}

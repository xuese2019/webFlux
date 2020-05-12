package com.example.webFlux.business.user.router;

import com.example.webFlux.business.user.handler.User2Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 路由-函数式编程
 */
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> users(User2Handler userHandler) {
        return RouterFunctions.nest(
//                设置统一请求前缀
                path("/api/user"),
                route(POST("/"), userHandler::save)
                        .andRoute(DELETE("/{uuid}"), userHandler::remove)
                        .andRoute(PUT("/{uuid}"), userHandler::edit)
                        .andRoute(GET("/{uuid}"), userHandler::one)
//                        json 参数
                        .andRoute(POST("/json/{pageSize}/{pageNow}")
                                        .and(contentType(MediaType.APPLICATION_JSON))
                                , userHandler::findAll)
//                        form 参数
                        .andRoute(POST("/form/{pageSize}/{pageNow}")
                                        .and(contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                , userHandler::findAll2)
        );
//        return RouterFunctions.route()
//                .POST("/user/user", RequestPredicates.accept(MediaType.APPLICATION_JSON), userHandler::add)
//                .DELETE("/user/{uuid}", userHandler::remove)
//                .PUT("/user/{uuid}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userHandler::edit)
//                .GET("/user/{uuid}", userHandler::one)
//                .POST("/user/{pageSize}/{pageNow}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userHandler::page)
//                .build();
    }
}

package com.example.webFlux.business.user.handler;


import com.example.webFlux.business.user.model.UserModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 功能处理类
 */
@Component
public class DemoHandler {

    /**
     * 前台传递json数据
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> json(ServerRequest request) {
        Mono<Map> body = request.bodyToMono(Map.class);
        return body.flatMap(map -> {
            String name = (String) map.get("account");
            System.out.println(name);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(BodyInserters.fromValue(name));
        });
    }

    /**
     * 前台传递APPLICATION_FORM_URLENCODED表单
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> formData(ServerRequest request) {
        // 获取form data,在方法中添加ServerWebExchange对象，使用方法getFormData
        ServerWebExchange exchange = request.exchange();
        Mono<MultiValueMap<String, String>> formData = exchange.getFormData();

        return formData.flatMap(map -> {
            System.out.println(map.get("account").get(0));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }

    /**
     * 前台传递MULTIPART_FORM_DATA表单
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> formData2(ServerRequest request) {
        // 获取form data,在方法中添加ServerWebExchange对象，使用方法getFormData
        ServerWebExchange exchange = request.exchange();
        Mono<MultiValueMap<String, String>> formData = exchange.getFormData();

        return formData.flatMap(map -> {
            System.out.println(map.get("account").get(0));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }

    /**
     * 路径参数
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUser(ServerRequest request) {
//        路径参数
        String account = request.pathVariable("account");
        UserModel userModel = new UserModel();
        userModel.setUuid("id");
        userModel.setAccount(account);
        userModel.setPassword("pwd");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(userModel));
    }
}

package com.example.webFlux.business.login.handler;

import com.example.webFlux.business.login.model.LoginModel;
import com.example.webFlux.business.user.handler.UserHandler;
import com.example.webFlux.util.check.CheckUtil;
import com.example.webFlux.util.jwt.JwtUtil;
import com.example.webFlux.util.md5.Md5Util;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class LoginHandler {

    private UserHandler userHandler;

    public LoginHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginModel.class)
                .flatMap(u -> {
//                    校验参数
                    String check = CheckUtil.check(u);
                    if (check != null && !check.isEmpty()) {
                        return ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .bodyValue(check);
                    }
//                    密码加密
                    u.setPassword(Md5Util.md5(u.getPassword()));
//正常逻辑
                    return this.userHandler.oneByAccount(u.getAccount())
                            .flatMap(m -> {
                                if (m.getStatusCode() == HttpStatus.NOT_FOUND) {
                                    return ServerResponse
                                            .status(HttpStatus.NOT_FOUND)
                                            .bodyValue("账号不存在");
                                }
                                boolean b = Objects.requireNonNull(m.getBody()).getPassword().equals(u.getPassword());
                                if (b) {
                                    String jwt = JwtUtil.createJwt(Objects.requireNonNull(m.getBody()).getUuid(), "user");
                                    return ServerResponse
                                            .ok()
                                            .body(BodyInserters.fromValue(jwt));
                                } else {
                                    return ServerResponse
                                            .status(HttpStatus.BAD_REQUEST)
                                            .bodyValue("账号或密码错误");
                                }
                            });
                })
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue("账号或密码不能为空"));
//        Mono<ResponseEntity<UserModel>> userModelMono = userHandler.oneByAccount(model.getAccount());
//        return userModelMono
//                .switchIfEmpty(Mono.error(new Throwable("账号或密码错误")))
//                .flatMap(userModel -> {
////                    可以进行具体业务逻辑操作
//                    boolean b = Objects.requireNonNull(userModel.getBody()).getPassword().equals(model.getPassword());
//                    if (b) {
//                        String jwt = JwtUtil.createJwt(userModel.getBody().getUuid(), "user");
//                        userModel.getBody().setPassword(jwt);
//                        return Mono.just(userModel);
//                    } else {
//                        return Mono.error(new Throwable("账号或密码错误"));
//                    }
//                });
    }
}

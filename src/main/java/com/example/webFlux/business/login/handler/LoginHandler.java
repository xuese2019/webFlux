package com.example.webFlux.business.login.handler;

import com.example.webFlux.business.login.model.LoginModel;
import com.example.webFlux.business.user.handler.UserHandler;
import com.example.webFlux.business.user.model.UserModel;
import com.example.webFlux.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class LoginHandler {

    private UserHandler userHandler;

    @Autowired
    public LoginHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    /**
     * 登录验证
     *
     * @param model
     * @return
     */
    public Mono<ResponseEntity<UserModel>> login(LoginModel model) {
        Mono<ResponseEntity<UserModel>> userModelMono = userHandler.oneByAccount(model.getAccount());
        return userModelMono
                .switchIfEmpty(Mono.error(new Throwable("账号或密码错误")))
                .flatMap(userModel -> {
//                    可以进行具体业务逻辑操作
                    boolean b = Objects.requireNonNull(userModel.getBody()).getPassword().equals(model.getPassword());
                    if (b) {
                        String jwt = JwtUtil.createJwt(userModel.getBody().getUuid(), "user");
                        userModel.getBody().setPassword(jwt);
                        return Mono.just(userModel);
                    } else {
                        return Mono.error(new Throwable("账号或密码错误"));
                    }
                });
    }
}

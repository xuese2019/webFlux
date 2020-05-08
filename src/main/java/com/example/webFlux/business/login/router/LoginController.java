package com.example.webFlux.business.login.router;

import com.example.webFlux.business.login.handler.LoginHandler;
import com.example.webFlux.business.login.model.LoginModel;
import com.example.webFlux.business.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
public class LoginController {

    private LoginHandler loginHandler;

    @Autowired
    public LoginController(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserModel>> login(LoginModel model) {
        return loginHandler.login(model);
    }
}

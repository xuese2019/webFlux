package com.example.webFlux.business.user.router;

import com.example.webFlux.business.user.handler.UserHandler;
import com.example.webFlux.business.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserHandler userHandler;

    @Autowired

    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping("/user")
    public Mono<ResponseEntity<UserModel>> add(@Valid @RequestBody UserModel model) {
        return userHandler.save(model);
    }

    @DeleteMapping("/user/{uuid}")
    public Mono<ResponseEntity<Void>> remove(@PathVariable("uuid") String uuid) {
        return userHandler.remove(uuid);
    }

    @PutMapping("/user/{uuid}")
    public Mono<ResponseEntity<UserModel>> edit(@PathVariable("uuid") String uuid, @Valid @RequestBody UserModel model) {
        model.setUuid(uuid);
        return userHandler.edit(model);
    }

    @GetMapping("/user/{uuid}")
    public Mono<ResponseEntity<UserModel>> one(@PathVariable("uuid") String uuid) {
        return userHandler.one(uuid);
    }

    @PostMapping("/user/{pageSize}/{pageNow}")
    public Flux<UserModel> page(@Min(value = 1) @PathVariable("pageSize") int pageSize,
                                @Min(value = 0) @PathVariable("pageNow") int pageNow,
                                @RequestBody(required = false) UserModel model) {
        return userHandler.findAll(pageSize, pageNow, model);
    }
}

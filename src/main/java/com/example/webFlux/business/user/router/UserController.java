package com.example.webFlux.business.user.router;

import com.example.webFlux.business.user.handler.UserHandler;
import com.example.webFlux.business.user.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/api2/user")
public class UserController {

    private UserHandler userHandler;

    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping
    public Mono<ResponseEntity<UserModel>> add(@Validated UserModel model) {
        return userHandler.save(model);
    }

    @DeleteMapping("/{uuid}")
    public Mono<Void> remove(@PathVariable("uuid") String uuid) {
        return userHandler.remove(uuid);
    }

    @PutMapping("/{uuid}")
    public Mono<ResponseEntity<UserModel>> edit(@PathVariable("uuid") String uuid, @Valid UserModel model) {
        model.setUuid(uuid);
        return userHandler.edit(model);
    }

    @GetMapping("/{uuid}")
    public Mono<ResponseEntity<UserModel>> one(@PathVariable("uuid") String uuid) {
        return userHandler.one(uuid);
    }

    /**
     * 普通的一次性返回
     *
     * @param pageSize
     * @param pageNow
     * @param model
     * @return
     */
    @PostMapping("/{pageSize}/{pageNow}")
    public Flux<UserModel> page(@Min(value = 1) @PathVariable("pageSize") int pageSize,
                                @Min(value = 0) @PathVariable("pageNow") int pageNow,
                                UserModel model) {
        return userHandler.findAll(pageSize, pageNow, model);
    }

    /**
     * 用流的方式逐条返回
     * produces = MediaType.TEXT_EVENT_STREAM_VALUE
     *
     * @param pageSize
     * @param pageNow
     * @param model
     * @return
     */
    @PostMapping(value = "/stream/{pageSize}/{pageNow}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserModel> pageStream(@Min(value = 1) @PathVariable("pageSize") int pageSize,
                                      @Min(value = 0) @PathVariable("pageNow") int pageNow,
                                      UserModel model) {
        return userHandler.findAll(pageSize, pageNow, model);
    }
}

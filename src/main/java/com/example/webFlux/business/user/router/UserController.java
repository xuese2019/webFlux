package com.example.webFlux.business.user.router;

import com.example.webFlux.business.user.handler.UserHandler;
import com.example.webFlux.business.user.model.UserModel;
import com.example.webFlux.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Value("${download-path.word-path}")
    private String path;

    private UserHandler userHandler;

    @Autowired
    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping
    public Mono<ResponseEntity<UserModel>> add(@Validated UserModel model) {
        return userHandler.save(model);
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @PostMapping("/portrait")
    public Mono<ResponseEntity<String>> portrait(@RequestPart("file") FilePart file) {
        try {
            Path tempFile = Files.createTempFile(Paths.get(path),"temp",".jpg");
            file.transferTo(tempFile.toFile());
        } catch (IOException e) {
            throw new MyException("头像上传失败");
        }
        return Mono.just(ResponseEntity.ok().body(file.filename()));
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

    @PostMapping("/{pageSize}/{pageNow}")
    public Flux<UserModel> page(@Min(value = 1) @PathVariable("pageSize") int pageSize,
                                @Min(value = 0) @PathVariable("pageNow") int pageNow,
                                UserModel model) {
        return userHandler.findAll(pageSize, pageNow, model);
    }
}

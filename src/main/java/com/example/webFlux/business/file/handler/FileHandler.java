package com.example.webFlux.business.file.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FileHandler {

    public Mono<ServerResponse> upload(ServerRequest request) {
//        try {
////            FileUtil.getType(file);
//            Path tempFile = Files.createTempFile(Paths.get(path), "user", ".jpg");
//            file.transferTo(tempFile.toFile());
//        } catch (IOException e) {
//            throw new MyException("头像上传失败");
//        }
//        return Mono.just(ResponseEntity.ok().body(file.filename()));
        return ServerResponse.ok().build();
    }
}

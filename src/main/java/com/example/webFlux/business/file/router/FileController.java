package com.example.webFlux.business.file.router;

import com.example.webFlux.business.file.util.FileUtil;
import com.example.webFlux.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${download-path.word-path}")
    private String path;

    @PostMapping("/upload")
    public Mono<ResponseEntity<String>> upload(@RequestPart("file") FilePart file) {
        try {
//            FileUtil.getType(file);
            Path tempFile = Files.createTempFile(Paths.get(path), "user", ".jpg");
            file.transferTo(tempFile.toFile());
        } catch (IOException e) {
            throw new MyException("头像上传失败");
        }
        return Mono.just(ResponseEntity.ok().body(file.filename()));
    }
}

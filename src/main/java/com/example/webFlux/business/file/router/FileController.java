package com.example.webFlux.business.file.router;

import com.example.webFlux.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api2/file")
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

    @GetMapping("/download")
    public Mono<Void> downloadByWriteWith(ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parallel.png");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);

        Resource resource = new ClassPathResource("parallel.png");
        File file = resource.getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }

}

package com.example.webFlux;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.webFlux.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class AllException {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> throwable(Throwable e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> unsupportedOperationException(UnsupportedOperationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("令牌信息获取错误");
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> notFoundException(MyException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<String> jWTDecodeException(JWTDecodeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("令牌解析错误");
    }

}

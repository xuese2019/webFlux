package com.example.webFlux.exception;

/**
 * 资源未找到异常
 */
public class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
    }
}

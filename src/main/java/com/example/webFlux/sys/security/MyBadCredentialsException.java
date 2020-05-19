package com.example.webFlux.sys.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class MyBadCredentialsException extends BadCredentialsException {
    public MyBadCredentialsException(String msg) {
        super(msg);

    }
}

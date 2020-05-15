package com.example.webFlux.sys.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.example.webFlux.util.jwt.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 身份在验证管理器
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToekn = authentication.getCredentials().toString();
        try {
            Claim iss = JwtUtil.getClaim(authToekn, "iss");
            if (iss == null) {
                throw new JWTDecodeException("令牌解析错误");
            }
            if (!JwtUtil.ISS.equals(iss.asString())) {
                throw new BadCredentialsException("非法令牌");
            }

            Claim claim = JwtUtil.getClaim(authToekn, "user");
            // 此处应该列出token中携带的角色表。
            List<String> roles = new ArrayList<>();
            roles.add("user");
            if (claim != null) {
                Authentication authentication1 = new UsernamePasswordAuthenticationToken(
                        claim.asString(),
                        null,
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                return Mono.just(authentication1);
            } else {
                throw new JWTDecodeException("令牌解析错误");
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}

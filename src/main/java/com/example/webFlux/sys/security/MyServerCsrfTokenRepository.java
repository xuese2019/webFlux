package com.example.webFlux.sys.security;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * csrf token
 */
@Component
public class MyServerCsrfTokenRepository implements ServerCsrfTokenRepository {
    /**
     * 生成csrf token
     *
     * @param serverWebExchange
     * @return
     */
    @Override
    public Mono<CsrfToken> generateToken(ServerWebExchange serverWebExchange) {
        String csrf = serverWebExchange.getRequest().getHeaders().getFirst("_csrf");
        DefaultCsrfToken defaultCsrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "flux", UUID.randomUUID().toString());
        return Mono.just(defaultCsrfToken);
    }

    @Override
    public Mono<Void> saveToken(ServerWebExchange serverWebExchange, CsrfToken csrfToken) {
        serverWebExchange.getResponse().getHeaders().add("_csrf", csrfToken.getToken());
        return Mono.empty();
    }

    @Override
    public Mono<CsrfToken> loadToken(ServerWebExchange serverWebExchange) {
        String csrf = serverWebExchange.getRequest().getHeaders().getFirst("_csrf");
        DefaultCsrfToken defaultCsrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "flux", csrf);
        return Mono.just(defaultCsrfToken);
    }
}

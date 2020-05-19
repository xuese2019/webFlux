package com.example.webFlux.sys.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 过滤连
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(
                        "/",
                        "/webjars/**",
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/favicon.ico",
                        "/page/**",
                        "/login/**"
                ).permitAll()
                .anyExchange().authenticated()
                .and().build();

//        return http
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .securityContextRepository(securityContextRepository)
////                .authenticationManager(authenticationManager)
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .pathMatchers(
//                        "/",
//                        "/webjars/**",
//                        "/css/**",
//                        "/img/**",
//                        "/js/**",
//                        "/favicon.ico",
//                        "/page/**",
//                        "/login/**"
//                ).permitAll()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .build();
    }
}

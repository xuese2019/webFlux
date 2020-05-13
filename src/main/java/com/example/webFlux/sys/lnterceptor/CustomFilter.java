package com.example.webFlux.sys.lnterceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.example.webFlux.util.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 简易路径过滤器
 */
@Component
@Order(-1)
@Slf4j
public class CustomFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
//        是否需要拦截
        boolean filter = isFilter(request.getPath().toString());
        if (!filter) {
            log.info("当前请求被拦截的路径：{}", (request.getPath()));
//            token是否合法
            HttpHeaders headers = request.getHeaders();
            ResponseEntity<String> token = isToken(headers);
            HttpStatus statusCode = token.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                return webFilterChain.filter(serverWebExchange);
            } else {
                ServerHttpResponse response = serverWebExchange.getResponse();
                DataBuffer buffer = response.bufferFactory().wrap(token.getBody() != null ? token.getBody().getBytes() : "未指定错误".getBytes());
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }

    /**
     * 判断路径是否需要拦截
     *
     * @param path
     * @return
     */

    private static boolean isFilter(String path) {
        String PATH = "/";
        if (PATH.equals(path)) {
            return true;
        }
        String PATH2 = "/page/";
        if (path.startsWith(PATH2)) {
            return true;
        }
        List<String> list = list();
        AtomicReference<Boolean> b = new AtomicReference<>(false);
        list.forEach(k -> {
            boolean boo = path.endsWith(k);
            if (boo) {
                b.set(true);
            }
        });
        return b.get();
    }


    /**
     * 需要过滤的路径集合
     *
     * @return
     */

    private static List<String> list() {
        List<String> list = new ArrayList<>();
        list.add(".ico");
        list.add(".css");
        list.add(".js");
        list.add(".ttf");
        list.add(".woff");
        list.add(".woff2");
        list.add(".css.map");
        list.add(".js.map");
        list.add(".jpg");
        list.add("/login");
        return list;
    }


    /**
     * 头部获取token并判断是否合法
     *
     * @param headers
     * @return
     */

    private static ResponseEntity<String> isToken(HttpHeaders headers) {
        String token = headers.getFirst("auth");
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("令牌错误，请从新登录", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            try {
                Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
                if (stringClaimMap != null) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("令牌错误，请从新登录", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (TokenExpiredException | JWTDecodeException ignored) {
                return new ResponseEntity<>("令牌错误，请从新登录", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}

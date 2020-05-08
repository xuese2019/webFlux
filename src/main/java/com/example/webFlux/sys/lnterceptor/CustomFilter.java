package com.example.webFlux.sys.lnterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * 简易路径过滤器
 */
//@Component
//@Order(-1)
@Slf4j
public class CustomFilter {
}
/*
implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
//        ServerHttpRequest request = serverWebExchange.getRequest();
//        ServerHttpResponse response = serverWebExchange.getResponse();
////        是否需要拦截
//        if (!isFilter(request.getPath().toString())) {
//            log.info("当前请求被拦截的路径：{}", (request.getPath()));
////            token是否合法
//            HttpHeaders headers = request.getHeaders();
//            isToken(headers, response);
//        }
        return webFilterChain.filter(serverWebExchange);
    }

    */
/**
 * 判断路径是否需要拦截
 *
 * @param path
 * @return 需要过滤的路径集合
 * @return 头部获取token并判断是否合法
 * @param headers
 * @return
 *//*

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
                return;
            }
        });
        return b.get();
    }

    */
/**
 * 需要过滤的路径集合
 *
 * @return
 *//*

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
        list.add("/login");
        return list;
    }

    */
/**
 * 头部获取token并判断是否合法
 *
 * @param headers
 * @return
 *//*

    private static void isToken(HttpHeaders headers, ServerHttpResponse response) {
        List<String> list = headers.get(JwtUtil.TOKEN);
        if (list == null || list.size() <= 0) {
            response(response);
        } else {
            try {
                Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(list.get(0));
            } catch (TokenExpiredException e) {
                log.error(e.getMessage());
                response(response);
            }
        }
    }

    private static void response(ServerHttpResponse response) {
        DataBuffer buffer = response.bufferFactory().wrap("令牌错误或过期,请从新登录".getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.writeWith(Mono.just(buffer)).subscribe();
    }
}
*/

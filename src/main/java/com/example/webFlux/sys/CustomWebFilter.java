package com.example.webFlux.sys;

/**
 * webFlux过滤器
 */
//@Component
public class CustomWebFilter {
}
//        implements WebFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        MultiValueMap<String, String> queryParams = request.getQueryParams();
//        if (queryParams == null || StringUtils.isEmpty(queryParams.getFirst("token"))) {
//            Map<String, String> resultMap = new HashMap<>();
//            resultMap.put("code", "401");
//            resultMap.put("msg", "非法请求");
//            byte[] datas = new byte[0];
//            try {
//                datas = new ObjectMapper().writeValueAsBytes(resultMap);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            ServerHttpResponse response = exchange.getResponse();
//            DataBuffer buffer = response.bufferFactory().wrap(datas);
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//            return response.writeWith(Mono.just(buffer));
//        }
//
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            ServerHttpResponse response = exchange.getResponse();
//            //Manipulate the response in some way
//        }));
//    }
//}

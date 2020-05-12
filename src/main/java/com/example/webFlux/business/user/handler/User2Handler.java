package com.example.webFlux.business.user.handler;

import com.example.webFlux.business.user.model.UserModel;
import com.example.webFlux.business.user.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class User2Handler {

    private UserRepository userRepository;

    public User2Handler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新增
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<UserModel> model = request.bodyToMono(UserModel.class);
        return model.map(u -> {
            u.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            return u;
        }).flatMap(u -> {
            UserModel model1 = new UserModel();
            model1.setAccount(u.getAccount());
            Example<UserModel> example = Example.of(model1);
            return userRepository.findOne(example)
                    .flatMap(m -> {
//                        NOT_ACCEPTABLE 406
                        return ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(Mono.just("账号重复"));
                    })
                    .switchIfEmpty(
                            ServerResponse
                                    .ok().
                                    contentType(MediaType.APPLICATION_JSON)
                                    .body(this.userRepository.saveAll(model), UserModel.class)
                    );
        });
    }

    /**
     * 根据id删除
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return this.userRepository.findById(uuid)
                .flatMap(u -> {
                    return this.userRepository.delete(u)
                            .then(ServerResponse.ok().build());
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> edit(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(UserModel.class)
                .flatMap(o -> {
                    return userRepository.findById(uuid)
                            .map(m -> {
                                m.setPassword(o.getPassword());
                                return o;
                            })
                            .flatMap(u -> {
                                return ServerResponse
                                        .ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(this.userRepository.save(o), UserModel.class);
                            })
                            .switchIfEmpty(ServerResponse.notFound().build());
                });
    }

    /**
     * 根据id获取单个
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> one(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return userRepository.findById(uuid)
                .map(u -> {
                    u.setPassword(null);
                    return u;
                })
                .flatMap(u -> {
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(u);
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * 根据account获取单个
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> oneByAccount(ServerRequest request) {
        String uuid = request.pathVariable("account");
        return userRepository.findById(uuid)
                .map(u -> {
                    u.setPassword(null);
                    return u;
                })
                .flatMap(u -> {
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(u);
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * 分页条件查询
     * json参数
     * <p>
     * //                    Example<UserModel> example = Example.of(u);
     * //                            .body(this.userRepository.findAll(example, Sort.by(orders))
     * //                                            .skip((pageSize - 1) * pageNow)
     * //                                            .limitRate(pageSize)
     * //                                    , UserModel.class);
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        int pageSize = Integer.parseInt(request.pathVariable("pageSize"));
        int pageNow = Integer.parseInt(request.pathVariable("pageNow"));
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.asc("account"));
        return request.bodyToMono(UserModel.class)
                .filter(u -> u.getAccount() != null)
                .flatMap(u -> {
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(this.userRepository.findAllByUserModel(u, Sort.by(orders))
                                            .skip((pageSize - 1) * pageNow)
                                            .limitRate(pageSize)
                                    , UserModel.class);
                })
                .switchIfEmpty(
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(this.userRepository.findAll(Sort.by(orders))
                                                .skip((pageSize - 1) * pageNow)
                                                .limitRate(pageSize)
                                        , UserModel.class)
                );
//        json
//        return request.bodyToMono(UserModel.class)
//                .filter(u -> u.getAccount() != null)
//                .flatMap(u -> {
//                    return ServerResponse
//                            .ok()
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .body(this.userRepository.findAllByUserModel(u, Sort.by(orders))
//                                            .skip((pageSize - 1) * pageNow)
//                                            .limitRate(pageSize)
//                                    , UserModel.class);
//                })
//                .switchIfEmpty(
//                        ServerResponse
//                                .ok()
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(this.userRepository.findAll(Sort.by(orders))
//                                                .skip((pageSize - 1) * pageNow)
//                                                .limitRate(pageSize)
//                                        , UserModel.class)
//                );
    }

    /**
     * 分页条件查询
     * form参数
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAll2(ServerRequest request) {
        int pageSize = Integer.parseInt(request.pathVariable("pageSize"));
        int pageNow = Integer.parseInt(request.pathVariable("pageNow"));
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.asc("account"));
//        form
        return request.formData()
                .filter(u -> u.getFirst("account") != null)
                .flatMap(u -> {
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(this.userRepository.findAllByAccountLike(u.getFirst("account"), Sort.by(orders))
                                            .skip((pageSize - 1) * pageNow)
                                            .limitRate(pageSize)
                                    , UserModel.class);
                })
                .switchIfEmpty(
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(this.userRepository.findAll(Sort.by(orders))
                                                .skip((pageSize - 1) * pageNow)
                                                .limitRate(pageSize)
                                        , UserModel.class)
                );
    }

}

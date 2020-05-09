package com.example.webFlux.business.user.handler;

import com.example.webFlux.business.user.model.UserModel;
import com.example.webFlux.business.user.repository.UserRepository;
import com.example.webFlux.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserHandler {

    private UserRepository userRepository;

    @Autowired

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    public Mono<ResponseEntity<UserModel>> save(UserModel model) {
        model.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        UserModel model1 = new UserModel();
        model1.setAccount(model.getAccount());
        Example<UserModel> example = Example.of(model1);
        return userRepository.findOne(example)
                .map(u -> {
//                    查询有值，说明账号冲突，直接抛异常
                    throw new MyException("账号冲突");
                })
                //将前台传过来的值替换至流中
                .switchIfEmpty(Mono.just(model))
                .flatMap(u -> {
                    return userRepository.save(model)
//                            .map(ResponseEntity::ok);
                            .map(i -> {
                                i.setPassword(null);
                                return ResponseEntity.ok(i);
                            });
                });
    }

    /**
     * 根据id删除
     *
     * @param uuid
     * @return
     */
    public Mono<Void> remove(String uuid) {
        return userRepository.deleteById(uuid);
    }

    /**
     * 修改
     *
     * @param model
     * @return
     */
    public Mono<ResponseEntity<UserModel>> edit(UserModel model) {
        model.setAccount(null);
        return userRepository.findById(model.getUuid())
                .flatMap(u -> {
                    if (model.getPassword() != null) {
                        u.setPassword(model.getPassword());
                    }
                    return userRepository.save(u)
                            .map(i -> {
                                i.setPassword(null);
                                return ResponseEntity.ok(i);
                            });
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * 根据id获取单个
     *
     * @param uuid
     * @return
     */
    public Mono<ResponseEntity<UserModel>> one(String uuid) {
        return userRepository.findById(uuid)
                .map(u -> {
                    u.setPassword(null);
                    return ResponseEntity.ok(u);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * 根据account获取单个
     *
     * @param account
     * @return
     */
    public Mono<ResponseEntity<UserModel>> oneByAccount(String account) {
        UserModel model = new UserModel();
        model.setAccount(account);
        Example<UserModel> example = Example.of(model);
        return userRepository.findOne(example)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * 分页条件查询
     *
     * @param pageSize
     * @param pageNow
     * @param model
     * @return
     */
    public Flux<UserModel> findAll(int pageSize, int pageNow, UserModel model) {
//        Example<UserModel> example = Example.of(model);
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.asc("account"));
        if (model.getAccount() == null || model.getAccount().isEmpty()) {
            return userRepository.findAll(Sort.by(orders))
                    .skip((pageSize - 1) * pageNow)
                    .limitRate(pageSize);
        }
        return userRepository.findAllByUserModel(model, Sort.by(orders))
                .skip((pageSize - 1) * pageNow)
                .limitRate(pageSize);
    }

}

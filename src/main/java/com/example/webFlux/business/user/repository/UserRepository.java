package com.example.webFlux.business.user.repository;

import com.example.webFlux.business.user.model.UserModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 数据库访问层
 * ReactiveMongoRepository 类默认提供基本操作实现
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<UserModel, String> {

    /**
     * 自定义根据某个字段模糊查询带排序功能，方法名成定义参考POJO规则
     *
     * @param account
     * @param sort
     * @return
     */
    Flux<UserModel> findAllByAccountLike(String account, Sort sort);

    /**
     * 指明sql条件
     * 使用fields来设置返回的字段,1为随意指定
     *
     * @param account
     * @param sort
     * @return
     */
    @Query(value = "{'account': ?0}", fields = "{'account': 1}")
    Flux<UserModel> findAllByAccount(String account, Sort sort);

    /**
     * 根据姓名查询
     *
     * @param account
     * @return
     */
    Mono<UserModel> findAllByAccount(String account);

    /**
     * 指明sql条件
     * 采用对象入参方式，并且带有排序功能，而且模糊查询，并且返回指定的列，id列为默认包含
     *
     * @param model
     * @param sort
     * @return
     */
    @Query(value = "{'account': {$regex: ?#{[0].account}}}", fields = "{'account': 1}")
    Flux<UserModel> findAllByUserModel(UserModel model, Sort sort);
}

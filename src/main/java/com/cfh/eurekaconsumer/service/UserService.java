package com.cfh.eurekaconsumer.service;

import com.cfh.eurekaconsumer.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RestTemplate restTemplate;

    //使用ignoreExceptions可以让指定的异常不触发降级策略而是直接抛出
    //使用commandKey与groupkey参数指定命令名与命令组名（默认同组的命令使用同一个线程池）
    @HystrixCommand(fallbackMethod = "fallback",ignoreExceptions = NullPointerException.class,
    commandKey = "findUser",groupKey = "userGroup")
    @CacheResult(cacheKeyMethod = "getCacheKey")//开启缓存
    public User findUser(Integer id){//指定缓存的key
        return restTemplate.getForObject("http://hello-service/user/{1}",User.class,id);
    }

    public String getCacheKey(Integer id){
        return String.valueOf(id);
    }

    //注意降级策略的返回值必须与可能触发降级策略的调用相同
    public User fallback(Integer id,Throwable t){//使用参数t获取造成服务降级的异常
        log.info(t.getMessage());
        User defaultUser = new User(3,"xjf");
        return defaultUser;
    }

    @HystrixCommand
    @CacheRemove(commandKey = "findUser")//由于更新user会导致缓存失效所以需要使用@CacheRemove注解清缓存
    public void updateUser(Integer id){
        log.info("清空失效缓存");
        User updateUser = new User(id,"newName");
        restTemplate.postForEntity("http://hello-service/user/update/{1}",updateUser,void.class,id);
    }
}

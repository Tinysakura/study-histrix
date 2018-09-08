package com.cfh.eurekaconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")//使用HystrixCommand注解指定服务的回调方法
    public String helloService(){
        return restTemplate.getForObject("http://hello-service/hello",String.class);
    }

    public String fallback(){
        return "error";
    }

}

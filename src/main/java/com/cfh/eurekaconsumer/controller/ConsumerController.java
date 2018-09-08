package com.cfh.eurekaconsumer.controller;

import com.cfh.eurekaconsumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private HelloService helloService;

    @RequestMapping("/consumer")
    public String consumer(){
        return restTemplate.getForEntity("http://hello-service/hello",String.class).getBody();
    }

    @RequestMapping("/consumer1")
    public String consumer1(){
        return helloService.helloService();
    }
}

package com.cfh.eurekaconsumer.controller;

import com.cfh.eurekaconsumer.pojo.User;
import com.cfh.eurekaconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/{id}")
    public User findUser(@PathVariable("id") Integer id){
        //hystrix的缓存只在一次controller请求中有效
        userService.findUser(id);
        userService.updateUser(id);
        return userService.findUser(id);
    }

    @RequestMapping(value = "/user/update/{id}")
    public void updateUser(@PathVariable("id") Integer id){
        userService.updateUser(id);
    }
}

package com.cfh.eurekaconsumer.controller;

import com.cfh.eurekaconsumer.pojo.User;
import com.cfh.eurekaconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/user/collapser")
    public List<User> testCollapser() throws Exception{
        List<User> users = new ArrayList<>();

        User user1 = userService.findUser2(1);
        User user2 = userService.findUser2(2);

        //故意让线程sleep3000ms以错过请求窗口的合并时间
        Thread.sleep(3000);

        User user3 = userService.findUser2(1);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        return users;
    }
}

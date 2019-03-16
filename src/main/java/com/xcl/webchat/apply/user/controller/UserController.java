package com.xcl.webchat.apply.user.controller;

import com.xcl.webchat.apply.user.entity.User;
import com.xcl.webchat.apply.user.service.UserService;
import com.xcl.webchat.core.bean.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :xiaochanglu
 * @Description :
 * @date :2019/3/16 10:10
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getOne")
    private ResultBean<User> getOne(Integer user_id){
        return new ResultBean<User>().data(userService.getOne(user_id));
    }
    @GetMapping("/getOtherOne")
    private ResultBean<User> getOtherOne(Integer user_id){
        return new ResultBean<User>().data(userService.getOtherOne(user_id));
    }
}

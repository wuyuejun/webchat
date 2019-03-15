package com.xcl.webchat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author :xiaochanglu
 * @Description :聊天  控制器
 * @date :2019/3/14 8:58
 */
@Slf4j
@Controller
public class ChatController {
    @RequestMapping("/cunstomer/{name}")
    public String cunstomer(@PathVariable String name, Model model){
        try{
            System.out.println("跳转到 cunstomer 的页面上");
            model.addAttribute("username",name);
            return "Cunstomer";
        }
        catch (Exception e){
            log.error("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/client/{name}")
    public String client(@PathVariable String name, Model model){
        try{
            System.out.println("跳转到 client 的页面上");
            model.addAttribute("username",name);
            return "client";
        }
        catch (Exception e){
            log.error("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            return "error";
        }
    }
}

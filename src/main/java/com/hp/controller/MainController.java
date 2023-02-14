package com.hp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    /**
     * 进入到系统登录页面
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 系统主页面
     */
    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}

package com.xjf.springboot.controller;

import com.xjf.springboot.domain.User;
import com.xjf.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 主页控制器
 * @author xjf
 * @date 2019/2/5 10:10
 */
@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        model.addAttribute("errorMsg","登录失败，用户名或密码错误！");

        return "login";
    }

    /**
     * 获取注册页面
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String registerUser(User user){
        userService.saveOrUpdateUser(user);

        return "redirect:/login";
    }
}

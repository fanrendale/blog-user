package com.xjf.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理 控制器
 * @author xjf
 * @date 2019/2/5 10:10
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * 获取后台管理主页面
     * @param model
     * @return
     */
    public ModelAndView listUsers(Model model){
        return new ModelAndView("admins/index","menuList",model);
    }
}

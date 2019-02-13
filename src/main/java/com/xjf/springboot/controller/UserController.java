package com.xjf.springboot.controller;

import com.xjf.springboot.domain.User;
import com.xjf.springboot.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xjf
 * @date 2019/1/28 13:56
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRespository userRespository;

    /**
     * 查询所有用户
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(Model model){
        model.addAttribute("userList",userRespository.findAll());
        model.addAttribute("title","用户管理");

        return new ModelAndView("users/list","userModel",model);
    }

    /**
     * 根据id查询用户
     * @param id
     * @param model
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView getUserById(@PathVariable("id") Long id,Model model){
        model.addAttribute("user",userRespository.findById(id).get());
        model.addAttribute("title","查看用户");

        return new ModelAndView("users/view","userModel",model);
    }

    /**
     * 获取创建表单页面
     * @param model
     * @return
     */
    @GetMapping("/form")
    public ModelAndView createForm(Model model){
        model.addAttribute("user",new User(null,null, null));
        model.addAttribute("title","创建用户");

        return new ModelAndView("users/form","userModel",model);
    }

    /**
     * 进行新增获取更新
     * @param user
     * @param model
     * @return
     */
    @PostMapping
    public ModelAndView saveOrUpdateUser(User user,Model model){
        userRespository.save(user);
        System.out.println("添加或修改成功");

        //重定向到首页
        return new ModelAndView("redirect:/users");
    }

    /**
     * 根据id进行删除
     * @param id
     */
    @GetMapping("/del/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id){
        userRespository.deleteById(id);

        //重定向到首页
        return new ModelAndView("redirect:/users");
    }

    /**
     * 获取修改用户的页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id,Model model){
        User user = userRespository.findById(id).get();
        System.out.println(user);
        model.addAttribute("user",user);
        model.addAttribute("title","修改用户");

        return new ModelAndView("users/form","userModel",model);
    }
}

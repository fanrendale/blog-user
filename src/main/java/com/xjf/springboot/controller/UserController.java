package com.xjf.springboot.controller;

import com.xjf.springboot.domain.User;
import com.xjf.springboot.repository.UserRespository;
import com.xjf.springboot.service.UserService;
import com.xjf.springboot.util.ConstraintViolationExceptionHandler;
import com.xjf.springboot.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xjf
 * @date 2019/1/28 13:56
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async",required = false) boolean async,
            @RequestParam(value = "pageIndex",required = false,defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize,
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            Model model){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<User> page = userService.listUsersByNameLike(name,pageable);
        List<User> list = page.getContent();        //当前所在页面数据列表

        model.addAttribute("userList",list);
        model.addAttribute("page",page);

        //这个返回方式不懂
        return new ModelAndView(async==true?"users/list::#mainContainerReplace":"users/list","userModel",model);
    }

    /**
     * 获取form表单页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model){
        model.addAttribute("user",new User(null,null,null,null));

        return new ModelAndView("users/add","userModel",model);
    }

    /**
     * 新建用户
     * @param user
     * @param model
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user,Model model){
        try {
            userService.saveOrUpdateUser(user);
        }catch (ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }


        return ResponseEntity.ok().body(new Response(true,"处理成功",user));
    }

    /**
     * 根据id进行删除
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id){
        try {
            userService.removeUser(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }


        return ResponseEntity.ok().body(new Response(true,"处理成功"));
    }

    /**
     * 获取修改用户的页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView modify(@PathVariable("id") Long id,Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);

        return new ModelAndView("users/edit","userModel",model);
    }
}

package com.meethere.controller.admin;

import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.entity.vo.MessageVo;
import com.meethere.service.UserService;
import com.meethere.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminUserController {
    @Autowired
    private UserService userService;


    @GetMapping("/user_manage")
    public String user_manage(Model model){
        Pageable user_pageable= PageRequest.of(0,10, Sort.by("id").ascending());
        Page<User> users=userService.findByUserID(user_pageable);
        model.addAttribute("total",users.getTotalPages());
        return "admin/user_manage";
    }

    @GetMapping("/user_add")
    public String user_add(){
        return "admin/user_add";
    }

    @GetMapping("/userList.do")
    @ResponseBody
    public List<User> userList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable user_pageable= PageRequest.of(page-1,10, Sort.by("id").ascending());
        Page<User> users=userService.findByUserID(user_pageable);
        return users.getContent();
    }


    @GetMapping("/user_edit")
    public String user_edit(Model model,int id){
        User user=userService.findById(id);
        model.addAttribute("user",user);
        return "admin/user_edit";
    }

    @PostMapping("/modifyUser.do")
    public void modifyUser(String userID,String oldUserID,String userName, String password, String email, String phone,
                        HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user=userService.findByUserID(oldUserID);
        user.setUserID(userID);
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        userService.updateUser(user);
        response.sendRedirect("user_manage");
    }

    @PostMapping("/addUser.do")
    public void addUser(String userID,String userName, String password, String email, String phone,
                        HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user=new User();
        user.setUserID(userID);
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPicture("");
        userService.create(user);
        response.sendRedirect("user_manage");
    }

    @PostMapping("/checkUserID.do")
    @ResponseBody
    public boolean checkUserID(String userID){
        int count=userService.countUserID(userID);
        return count < 1;
    }
    @PostMapping("/delUser.do")
    @ResponseBody
    public boolean delUser(int id){
        userService.delByID(id);
        return true;
    }
}

package com.meethere.controller.admin;

import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.entity.vo.MessageVo;
import com.meethere.service.UserService;
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
    public String user_manage(){
        return "admin/user_manage";
    }

    @GetMapping("/userList.do")
    @ResponseBody
    public Page<User> userList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable user_pageable= PageRequest.of(page-1,5, Sort.by("id").ascending());
        Page<User> users=userService.findByUserID(user_pageable);
        return users;
    }

    @PostMapping("/addUser.do")
    public void addUSer(String userID, String user_name, String password, String email, String phone, MultipartFile image,
                        HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user=new User();
        user.setUserID(userID);
        user.setUserName(user_name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
    }
}

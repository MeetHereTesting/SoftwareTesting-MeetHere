package com.meethere.controller.user;

import com.meethere.entity.User;
import com.meethere.service.UserService;
import com.meethere.service.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.PastOrPresent;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/loginCheck.do")
    public void login(String userID,String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user1=userService.checkLogin(userID,password);
        if(user1!=null){
            if(user1.getIsadmin()==0){
            request.getSession().setAttribute("user",user1);
            response.sendRedirect("/index");
            }
            else if(user1.getIsadmin()==1){
                request.getSession().setAttribute("admin",user1);
                response.sendRedirect("/admin_index");
                System.out.println("admin login!");
            }

        }
        else
            throw new LoginException("登录失败，用户名或密码错误！");

    }

    @PostMapping("/register.do")
    public String register(User user,HttpServletResponse response) throws IOException {
        userService.create(user);
        return "login";
    }

    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        System.out.println("log out success!");
        response.sendRedirect("/index");
    }

    @PostMapping("/updateUser.do")
    @ResponseBody
    public boolean updateUser(){
        return true;
    }
}

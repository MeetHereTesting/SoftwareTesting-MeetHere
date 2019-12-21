package com.meethere.controller.user;

import com.meethere.entity.User;
import com.meethere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/loginCheck")
    public void loginCheck(@ModelAttribute(value="user")User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user1=userService.checkLogin(user.getUserID(),user.getPassword());
        if(user1!=null){
            request.getSession().setAttribute("user",user1);
            response.sendRedirect("/index");
            System.out.println("success");
        }

    }
}

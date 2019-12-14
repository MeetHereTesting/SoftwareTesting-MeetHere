package com.meethere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(Model model){
        return "index";
    }

    @RequestMapping("/signup")
    public String signUp(Model model){
        return "signup";
    }

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/news")
    public String news(Model model){
        return "news";
    }

    @RequestMapping("/news_list")
    public String news_list(Model model){
        return "news_list";
    }
}

package com.meethere.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @GetMapping("/news")
    public String news(Model model){
    return "news";
}

    @GetMapping("/news_list")
    public String news_list(Model model){
        return "news_list";
    }
}

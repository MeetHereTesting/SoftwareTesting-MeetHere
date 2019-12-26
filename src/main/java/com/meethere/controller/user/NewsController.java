package com.meethere.controller.user;

import com.meethere.entity.News;
import com.meethere.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String news(Model model,int newsID){
        News news= newsService.findById(newsID);
        model.addAttribute("news",news);
        return "news";
    }

    @GetMapping("/news/getNewsList")
    @ResponseBody
    public Page<News> news_list(@RequestParam(value = "page",defaultValue = "1")int page){
        System.out.println("success");
        Pageable news_pageable= PageRequest.of(page-1,5, Sort.by("time").descending());
        return newsService.findAll(news_pageable);
    }

    @GetMapping("/news_list")
    public String news_list(Model model){
        Pageable news_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        List<News> news_list= newsService.findAll(news_pageable).getContent();
        model.addAttribute("news_list",news_list);
        model.addAttribute("total", newsService.findAll(news_pageable).getTotalPages());
        return "news_list";
    }
}

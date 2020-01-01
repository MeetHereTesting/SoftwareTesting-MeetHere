package com.meethere.controller.admin;

import com.meethere.entity.News;
import com.meethere.entity.User;
import com.meethere.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class AdminNewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news_manage")
    public String news_manage(Model model){
        Pageable news_pageable= PageRequest.of(0,10, Sort.by("time").ascending());
        Page<News> news=newsService.findAll(news_pageable);
        model.addAttribute("total",news.getTotalPages());
        return "admin/news_manage";
    }

    @RequestMapping("/news_add")
    public String news_add(){
        return "/admin/news_add";
    }

    @RequestMapping("/news_edit")
    public String news_edit(int newsID,Model model){
        News news=newsService.findById(newsID);
        model.addAttribute("news",news);
        return "/admin/news_edit";
    }

    @RequestMapping("/newsList.do")
    @ResponseBody
    public List<News> newsList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable news_pageable= PageRequest.of(page-1,10, Sort.by("time").descending());
        Page<News> news=newsService.findAll(news_pageable);
        return news.getContent();
    }

    @PostMapping("/delNews.do")
    @ResponseBody
    public boolean delNews(int newsID){
        newsService.delById(newsID);
        return true;

    }

    @PostMapping("/modifyNews.do")
    public void modifyNews(int newsID,String title,String content,HttpServletResponse response) throws IOException {
        News news= newsService.findById(newsID);
        news.setTitle(title);
        news.setContent(content);
        news.setTime(LocalDateTime.now());
        newsService.update(news);
        response.sendRedirect("news_manage");
    }

    @PostMapping("/addNews.do")
    public void addNews(String title, String content, HttpServletResponse response) throws IOException {
        News news= new News();
        news.setTitle(title);
        news.setContent(content);
        news.setTime(LocalDateTime.now());
        newsService.create(news);
        response.sendRedirect("news_manage");
    }

}

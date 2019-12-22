package com.meethere.controller.admin;

import com.meethere.entity.News;
import com.meethere.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AdminNewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news_manage")
    public String news_manage(Model model){
        return "admin/news_manage";
    }

    @PostMapping("/delNews.do")
    public void delNews(int newsID){
        newsService.delById(newsID);
    }

    @PostMapping("/modeifyNews.do")
    public void modifyNews(int newsID,String title,String content){
        News news= newsService.findById(newsID);
        news.setTitle(title);
        news.setContent(content);
        news.setTime(new Date());
        newsService.update(news);
    }

    @PostMapping("/addNews.do")
    public void addNews(String title,String content){
        News news= new News();
        news.setTitle(title);
        news.setContent(content);
        news.setTime(new Date());
        newsService.create(news);
    }

}

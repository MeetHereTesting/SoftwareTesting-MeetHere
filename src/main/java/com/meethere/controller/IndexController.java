package com.meethere.controller;

import com.meethere.entity.News;
import com.meethere.entity.User;
import com.meethere.entity.Venue;
import com.meethere.entity.vo.MessageVo;
import com.meethere.service.MessageService;
import com.meethere.service.MessageVoService;
import com.meethere.service.NewService;
import com.meethere.service.VenueService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private NewService newService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private MessageVoService messageVoService;

    @GetMapping("/index")
    public String index(Model model){
        Pageable venue_pageable= PageRequest.of(0,5, Sort.by("venueID").ascending());
        Pageable news_pageable= PageRequest.of(0,5, Sort.by("newsID").descending());
        Pageable message_pageable= PageRequest.of(0,5, Sort.by("messageID").descending());

        List<Venue> venue_list=venueService.findAll(venue_pageable).getContent();
        List<News> news_list=newService.findAll(news_pageable).getContent();
        List<MessageVo> message_list=messageVoService.findAll(message_pageable);

        model.addAttribute("user", null);
        model.addAttribute("news_list",news_list);
        model.addAttribute("venue_list",venue_list);
        model.addAttribute("message_list",message_list);
        return "index";
    }


    @GetMapping("/admin_index")
    public String admin_index(Model model){
        return "admin/admin_index";
    }

    @GetMapping("/user_manage")
    public String user_manage(Model model){
        return "admin/user_manage";
    }

    @GetMapping("/venue_manage")
    public String venue_manage(Model model){
        return "admin/venue_manage";
    }

    @GetMapping("/reservation_manage")
    public String reservation_manage(Model model){
        return "admin/reservation_manage";
    }

    @GetMapping("/news_manage")
    public String news_manage(Model model){
        return "admin/news_manage";
    }

    @GetMapping("/message_manage")
    public String message_manage(Model model){
        return "admin/message_manage";
    }
}

package com.meethere.controller.admin;

import com.meethere.entity.Message;
import com.meethere.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminMessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/message_manage")
    public String message_manage(Model model){
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());
        model.addAttribute("total",messageService.findAll(message_pageable).getTotalPages());
        return "admin/message_manage";
    }

    /**
     * 管理员看到的留言只是未审核的留言，不进行封装，只显示留言内容，用户id，状态，时间
     * @param page
     * @return
     */
    @GetMapping("/messageList.do")
    @ResponseBody
    public List<Message> messageList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable message_pageable= PageRequest.of(page-1,10, Sort.by("time").descending());
        return messageService.findWaitState(message_pageable).getContent();
    }

    @PostMapping("/passMessage.do")
    public void passMessage(int messageID){
        messageService.confirmMessage(messageID);
    }

    @PostMapping("/rejectMessage.do")
    public void rejectMessage(int messageID){
        messageService.rejectMessage(messageID);
    }

    @RequestMapping("/delMessage.do")
    public void delMessage(int messageID){
        messageService.delById(messageID);
    }

}

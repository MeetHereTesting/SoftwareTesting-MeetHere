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

@Controller
public class AdminMessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 管理员看到的留言不进行封装，显示留言内容，用户id，状态，时间
     * @param page
     * @return
     */
    @GetMapping("/messageList.do")
    @ResponseBody
    public Page<Message> messageList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable message_pageable= PageRequest.of(page-1,10, Sort.by("time").descending());
        return messageService.findAll(message_pageable);
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

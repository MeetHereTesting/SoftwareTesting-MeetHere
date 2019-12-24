package com.meethere.controller.admin;

import com.meethere.entity.Message;
import com.meethere.entity.vo.MessageVo;
import com.meethere.service.MessageService;
import com.meethere.service.MessageVoService;
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
    @Autowired
    private MessageVoService messageVoService;

    @GetMapping("/message_manage")
    public String message_manage(Model model){
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());
        model.addAttribute("total",messageService.findAll(message_pageable).getTotalPages());
        return "admin/message_manage";
    }

    /**
     *
     * @param page
     * @return
     */
    @GetMapping("/messageList.do")
    @ResponseBody
    public List<MessageVo> messageList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable message_pageable= PageRequest.of(page-1,10, Sort.by("time").descending());
        List<Message> messages=messageService.findWaitState(message_pageable).getContent();
        return messageVoService.returnVo(messages);
    }

    @PostMapping("/passMessage.do")
    @ResponseBody
    public boolean passMessage(int messageID){

        messageService.confirmMessage(messageID);
        return true;
    }

    @PostMapping("/rejectMessage.do")
    @ResponseBody
    public boolean rejectMessage(int messageID){

        messageService.rejectMessage(messageID);
        return true;
    }

    @RequestMapping("/delMessage.do")
    @ResponseBody
    public boolean delMessage(int messageID){
        messageService.delById(messageID);
        return true;
    }

}

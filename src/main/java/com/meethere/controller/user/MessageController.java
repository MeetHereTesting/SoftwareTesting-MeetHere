package com.meethere.controller.user;

import com.meethere.entity.Message;
import com.meethere.entity.News;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageVoService messageVoService;

    @GetMapping("/message_list")
    public String message_list(Model model,HttpServletRequest request){
        Pageable message_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        Page<Message> messages=messageService.findAll(message_pageable);
//        List<MessageVo> message_list=messageVoService.findAll(message_pageable);
//        List<MessageVo> message_list=messageVoService.returnVo(messages.getContent());

//        model.addAttribute("message_list",message_list);
        model.addAttribute("total",messages.getTotalPages());

        model.addAttribute("user_total",1);

        if(request.getSession().getAttribute("user")!=null){
            Pageable user_message_pageable = PageRequest.of(0,5, Sort.by("time").descending());
            model.addAttribute("user_total",messageService.findByUser(request,user_message_pageable).getTotalPages());

        }

        return "message_list";
    }

    //只显示通过状态的留言
    @GetMapping("/message/getMessageList")
    @ResponseBody
    public List<MessageVo> message_list(@RequestParam(value = "page",defaultValue = "1")int page){
        System.out.println("success");
        Pageable message_pageable= PageRequest.of(page-1,5, Sort.by("time").descending());
        Page<Message> messages=messageService.findPassState(message_pageable);
        List<MessageVo> message_list=messageVoService.returnVo(messages.getContent());

        return message_list;
    }

    //User的留言不管是否通过都显示
    @GetMapping("/message/findUserList")
    @ResponseBody
    public List<MessageVo> user_message_list(@RequestParam(value = "page",defaultValue = "1")int page,HttpServletRequest request){
        System.out.println("find user messages");
//        if(request.getSession().getAttribute("user")!=null) {
            Pageable message_pageable = PageRequest.of(page - 1, 5, Sort.by("time").descending());
            List<Message> user_messages = messageService.findByUser(request, message_pageable).getContent();
            return messageVoService.returnVo(user_messages);
//        }
//        return null;
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(String userID, String content, HttpServletResponse response) throws IOException {
        Message message=new Message();
        message.setUserID(userID);
        message.setContent(content);
        message.setState(1);
        message.setTime(LocalDateTime.now());
        messageService.create(message);
        response.sendRedirect("/message_list");
    }

    @PostMapping("/modifyMessage.do")
    @ResponseBody
    public boolean modifyMessage(int messageID,String content, HttpServletResponse response) throws IOException {
        Message message=messageService.findById(messageID);
        message.setContent(content);
        message.setTime(LocalDateTime.now());
        message.setState(1);
        messageService.update(message);
        return true;
    }

    @PostMapping("/delMessage.do")
    @ResponseBody
    public boolean delMessage(int messageID)
    {
        messageService.delById(messageID);
        return true;
    }

}

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        List<MessageVo> message_list=messageVoService.findAll(message_pageable);

//        model.addAttribute("message_list",message_list);
        model.addAttribute("total",messageService.findAll(message_pageable).getTotalPages());

        model.addAttribute("user_total",1);

        //如果user不为空进行渲染
        if(request.getSession().getAttribute("user")!=null){
            Pageable user_message_pageable = PageRequest.of(0,5, Sort.by("time").descending());
            List<Message> user_messages = messageService.findByUser(request,user_message_pageable).getContent();

//            model.addAttribute("user_message_list",messageVoService.returnVo(user_messages));
            model.addAttribute("user_total",messageService.findByUser(request,user_message_pageable).getTotalPages());
        }

        return "message_list";
    }

    @GetMapping("/message/getMessageList")
    @ResponseBody
    public List<MessageVo> message_list(@RequestParam(value = "page",defaultValue = "1")int page){
        System.out.println("success");
        Pageable message_pageable= PageRequest.of(page-1,5, Sort.by("time").descending());
        return messageVoService.findAll(message_pageable);
    }

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

}

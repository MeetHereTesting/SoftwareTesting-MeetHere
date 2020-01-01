package com.meethere.controller.user;

import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.service.MessageService;
import com.meethere.service.MessageVoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageService messageService;
    @MockBean
    private MessageVoService messageVoService;

    @Test
    public void fail_return_message_list_html_when_user_not_login() throws Exception {
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);
        Pageable message_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        when(messageService.findPassState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(any())).thenReturn(null);

        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/message_list")),"请登录！");
//        MvcResult result=mockMvc.perform(get("/message_list")).andExpect(status().is5xxServerError()).andReturn();
//        assertThrows(LoginException.class,()->mockMvc.perform(get("/message_list")),"请登录！");

    }

    @Test
    public void success_return_message_list_html_when_user_login() throws Exception {
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);

        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        Pageable message_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        Pageable user_message_pageable = PageRequest.of(0,5, Sort.by("time").descending());
        when(messageService.findPassState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(any())).thenReturn(null);
        when(messageService.findByUser(userID,user_message_pageable)).thenReturn(new PageImpl<>(messages,message_pageable,1));

        ResultActions perform=mockMvc.perform(get("/message_list").sessionAttr("user",user));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");
        assertModelAttributeAvailable(mv,"user_total");
        verify(messageService).findPassState(message_pageable);
        verify(messageVoService).returnVo(any());
        verify(messageService).findByUser(userID,user_message_pageable);
    }

    @Test
    public void return_pass_message_list()throws Exception {
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,2);
        List<Message> messages=new ArrayList<>();
        messages.add(message);
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());
        when(messageService.findPassState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(messages)).thenReturn(null);
        ResultActions perform=mockMvc.perform(get("/message/getMessageList"));
        perform.andExpect(status().isOk());
        verify(messageService).findPassState(any());
        verify(messageVoService).returnVo(any());
    }

    @Test
    public void success_return_user_message_list_when_user_login() throws Exception{
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,2);
        List<Message> messages=new ArrayList<>();
        messages.add(message);

        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        Pageable message_pageable = PageRequest.of(0, 5, Sort.by("time").descending());
        when(messageService.findByUser("user",message_pageable)).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(any())).thenReturn(null);

        ResultActions perform=mockMvc.perform(get("/message/findUserList").sessionAttr("user",user));
        perform.andExpect(status().isOk());
        verify(messageService).findByUser("user",message_pageable);
        verify(messageVoService).returnVo(any());

    }

    @Test
    public void fail_return_user_message_list_when_user_not_login() throws Exception{
        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/message/findUserList")),"请登录！");
    }

    @Test
    public void user_add_new_message()throws Exception {
        when(messageService.create(any())).thenReturn(1);
        ResultActions perform=mockMvc.perform(post("/sendMessage").param("userID","user").param("content","this is content"));
        perform.andExpect(redirectedUrl("/message_list"));
        verify(messageService).create(any());
    }


    @Test
    public void user_modify_message()throws Exception {
        when(messageService.findById(anyInt())).thenReturn(new Message());
        ResultActions perform=mockMvc.perform(post("/modifyMessage.do").param("messageID","1").param("userID","user").param("content","this is content"));
        perform.andExpect(content().string("true"));
        verify(messageService).update(any());
    }

    @Test
    public void user_del_message()throws Exception {
        ResultActions perform=mockMvc.perform(post("/delMessage.do").param("messageID","1"));
        perform.andExpect(content().string("true"));
        verify(messageService).delById(anyInt());

    }
}
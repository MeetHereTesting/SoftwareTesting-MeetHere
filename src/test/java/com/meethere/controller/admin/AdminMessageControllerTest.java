package com.meethere.controller.admin;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Message;
import com.meethere.service.MessageService;
import com.meethere.service.MessageVoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminMessageController.class)
class AdminMessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageService messageService;
    @MockBean
    private MessageVoService messageVoService;

    @Test
    public void return_message_manage_html() throws Exception {
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());
        when(messageService.findWaitState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));

        ResultActions perform=mockMvc.perform(get("/message_manage"));
        perform.andExpect(status().isOk());
        verify(messageService).findWaitState(any());
        MvcResult mvcResult=mockMvc.perform(get("/message_manage")).andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        assertModelAttributeAvailable(mv,"total");
        verify(messageService,times(2)).findWaitState(any());
    }

    @Test
    public void return_message_list_to_audit() throws Exception {
        int id=1;
        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());
        when(messageService.findWaitState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(messages)).thenReturn(null);
        ResultActions perform=mockMvc.perform(get("/messageList.do"));
        perform.andExpect(status().isOk());
        verify(messageService).findWaitState(any());
        verify(messageVoService).returnVo(any());
    }

    @Test
    public void admin_pass_message() throws Exception {
        ResultActions perform=mockMvc.perform(post("/passMessage.do").param("messageID","1"));
        perform.andExpect(status().isOk());
        verify(messageService).confirmMessage(1);
    }

    @Test
    public void admin_reject_message() throws Exception {
        ResultActions perform=mockMvc.perform(post("/rejectMessage.do").param("messageID","1"));
        perform.andExpect(status().isOk());
        verify(messageService).rejectMessage(1);
    }

    @Test
    public void admin_del_message() throws Exception {
        ResultActions perform=mockMvc.perform(post("/delMessage.do").param("messageID","1"));
        perform.andExpect(status().isOk());
        verify(messageService).delById(1);
    }
}
package com.meethere.controller;

import com.meethere.entity.Message;
import com.meethere.entity.News;
import com.meethere.entity.Venue;
import com.meethere.service.MessageService;
import com.meethere.service.MessageVoService;
import com.meethere.service.NewsService;
import com.meethere.service.VenueService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IndexController.class)
class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VenueService venueService;
    @MockBean
    private NewsService newsService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private MessageVoService messageVoService;

    @Test
    public void return_index_html() throws Exception {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);
        List<Venue> venues=new ArrayList<>();
        venues.add(venue);

        int id=1;
        String title="title";
        String content="this is content";
        LocalDateTime ldt=LocalDateTime.now();
        News news=new News(id,title,content,ldt);
        List<News> news1=new ArrayList<>();
        news1.add(news);

        LocalDateTime ldt1=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt1,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);

        Pageable venue_pageable= PageRequest.of(0,3, Sort.by("venueID").ascending());
        Pageable news_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        Pageable message_pageable= PageRequest.of(0,5, Sort.by("time").descending());


        when(venueService.findAll(any())).thenReturn(new PageImpl<>(venues,venue_pageable,1));
        when(newsService.findAll(any())).thenReturn(new PageImpl<>(news1,news_pageable,1));
        when(messageService.findPassState(any())).thenReturn(new PageImpl<>(messages,message_pageable,1));
        when(messageVoService.returnVo(any())).thenReturn(null);

        ResultActions perform=mockMvc.perform(get("/index"));
        perform.andExpect(status().isOk());
        verify(venueService).findAll(any());
        verify(newsService).findAll(any());
        verify(messageService).findPassState(any());
        verify(messageVoService).returnVo(any());

        MvcResult mvcResult=mockMvc.perform(get("/index")).andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        assertAll("",()-> assertModelAttributeAvailable(mv,"user"),
                             ()-> assertModelAttributeAvailable(mv,"news_list"),
                             ()->assertModelAttributeAvailable(mv,"venue_list"),
                             ()->assertModelAttributeAvailable(mv,"message_list"));

        verify(venueService,times(2)).findAll(any());
        verify(newsService,times(2)).findAll(any());
        verify(messageService,times(2)).findPassState(any());
        verify(messageVoService,times(2)).returnVo(any());
    }

    @Test
    public void return_admin_index_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/admin_index"));
        perform.andExpect(status().isOk());
    }
}
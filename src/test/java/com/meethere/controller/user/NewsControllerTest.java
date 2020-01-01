package com.meethere.controller.user;

import com.meethere.entity.News;
import com.meethere.service.NewsService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NewsController.class)
class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsService newsService;

    @Test
    public void return_news_detail_html() throws Exception {
        when(newsService.findById(1)).thenReturn(new News());
        ResultActions perform=mockMvc.perform(get("/news").param("newsID","1"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"news");
        verify(newsService).findById(1);
    }

    @Test
    public void return_news_list_page() throws Exception {
        List<News> newsList=new ArrayList<>();
        newsList.add(new News());
        Pageable news_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        when(newsService.findAll(news_pageable)).thenReturn(new PageImpl<>(newsList,news_pageable,1));

        ResultActions perform=mockMvc.perform(get("/news/getNewsList").param("page","1"));
        perform.andExpect(status().isOk());
        verify(newsService).findAll(news_pageable);
    }

    @Test
    public void return_news_list_html() throws Exception {
        List<News> newsList=new ArrayList<>();
        newsList.add(new News());
        Pageable news_pageable= PageRequest.of(0,5, Sort.by("time").descending());
        when(newsService.findAll(news_pageable)).thenReturn(new PageImpl<>(newsList,news_pageable,1));
        ResultActions perform=mockMvc.perform(get("/news_list").param("newsID","1"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"news_list");
        assertModelAttributeAvailable(mv,"total");
        verify(newsService,times(2)).findAll(news_pageable);
    }
}
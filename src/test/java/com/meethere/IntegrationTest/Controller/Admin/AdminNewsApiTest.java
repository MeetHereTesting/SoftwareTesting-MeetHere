package com.meethere.IntegrationTest.Controller.Admin;

import com.meethere.MeetHereApplication;
import com.meethere.entity.News;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminNewsApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void return_news_manage_html() throws Exception{
        ResultActions perform=mockMvc.perform(get("/news_manage"));
        perform.andExpect(status().isOk()).andDo(print());

        MvcResult mvcResult=mockMvc.perform(get("/news_manage")).andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        assertModelAttributeAvailable(mv,"total");
    }

    @Test
    public void return_news_add_html()throws Exception {
        ResultActions perform=mockMvc.perform(get("/news_add"));
        perform.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void return_news_edit_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/news_edit").param("newsID","1"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk()).andDo(print());
        assertModelAttributeAvailable(mv,"news");
    }

    @Test
    public void return_news_list() throws Exception {
        ResultActions perform=mockMvc.perform(get("/newsList.do").param("page","1"));
        perform.andExpect(status().isOk()).andDo(print());

    }

    @Test
    public void admin_del_news() throws Exception {
        ResultActions perform=mockMvc.perform(post("/delNews.do").param("newsID","1"));
        perform.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void admin_modify_news() throws Exception {
        ResultActions perform=mockMvc.perform(post("/modifyNews.do").param("newsID","1").param("title","this is title").
                param("content","this is content"));

        perform.andExpect(redirectedUrl("news_manage")).andDo(print());
    }

    @Test
    public void admin_add_news() throws Exception {
        ResultActions perform=mockMvc.perform(post("/addNews.do").param("title","this is title").param("content","this is content"));

        perform.andExpect(redirectedUrl("news_manage")).andDo(print());
    }
}

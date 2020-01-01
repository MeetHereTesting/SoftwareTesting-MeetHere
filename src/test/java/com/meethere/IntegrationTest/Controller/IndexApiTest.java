package com.meethere.IntegrationTest.Controller;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Message;
import com.meethere.entity.News;
import com.meethere.entity.Venue;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class IndexApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void return_index_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/index"));
        perform.andExpect(status().isOk());

        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        assertAll("",()-> assertModelAttributeAvailable(mv,"user"),
                ()-> assertModelAttributeAvailable(mv,"news_list"),
                ()->assertModelAttributeAvailable(mv,"venue_list"),
                ()->assertModelAttributeAvailable(mv,"message_list"));

    }

    @Test
    public void return_admin_index_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/admin_index"));
        perform.andExpect(status().isOk()).andDo(print());
    }
}

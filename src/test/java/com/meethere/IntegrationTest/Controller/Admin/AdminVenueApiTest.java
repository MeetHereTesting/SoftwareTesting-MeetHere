package com.meethere.IntegrationTest.Controller.Admin;

import com.meethere.MeetHereApplication;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminVenueApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void return_venue_manage_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/venue_manage"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");

    }

    @Test
    public void return_edit_venue() throws Exception {
        ResultActions perform=mockMvc.perform(get("/venue_edit").param("venueID","2"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue");

    }

    @Test
    public void return_venue_add_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/venue_add"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void return_venue_list()  throws Exception{

        ResultActions perform=mockMvc.perform(get("/venueList.do").param("page","1"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void admin_add_venue_picture_is_null()  throws Exception{
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
    }

    @Test
    public void admin_add_venue_picture_is_not_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
    }

    @Test
    public void admin_modify_venue_picture_is_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/modifyVenue.do")
                        .file(mockMultipartFile).param("venueID","2").param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
    }

    @Test
    public void admin_modify_venue_picture_is_not_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/modifyVenue.do")
                        .file(mockMultipartFile).param("venueID","2").param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
    }

    @Test
    public void admin_del_venue() throws Exception {
        ResultActions perform=mockMvc.perform(post("/delVenue.do").param("venueID","2"));
        perform.andExpect(status().isOk());
    }

    @Test
    public  void return_already_exist_same_venue_name()  throws Exception{
        ResultActions perform=mockMvc.perform(post("/checkVenueName.do").param("venueName","222"));
        perform.andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    public  void return_not_exist_same_venue_name()  throws Exception{
        ResultActions perform=mockMvc.perform(post("/checkVenueName.do").param("venueName","venue"));
        perform.andExpect(status().isOk()).andExpect(content().string("true"));

    }
}

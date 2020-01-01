package com.meethere.controller.admin;

import com.meethere.entity.Venue;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminVenueController.class)
class AdminVenueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VenueService venueService;

    @Test
    public void return_venue_manage_html() throws Exception {
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
        Pageable pageable= PageRequest.of(0,10, Sort.by("venueID").ascending());

        when(venueService.findAll(pageable)).thenReturn(new PageImpl<>(venues,pageable,1));
        ResultActions perform=mockMvc.perform(get("/venue_manage"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");
        verify(venueService).findAll(pageable);
    }

    @Test
    public void return_edit_venue() throws Exception {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);
        when(venueService.findByVenueID(venueID)).thenReturn(venue);

        ResultActions perform=mockMvc.perform(get("/venue_edit").param("venueID","1"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue");
        verify(venueService).findByVenueID(venueID);
    }

    @Test
    public void return_venue_add_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/venue_add"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void return_venue_list()  throws Exception{
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
        Pageable pageable= PageRequest.of(0,10, Sort.by("venueID").ascending());

        when(venueService.findAll(pageable)).thenReturn(new PageImpl<>(venues,pageable,1));
        ResultActions perform=mockMvc.perform(get("/venueList.do").param("page","1"));
        perform.andExpect(status().isOk());
        verify(venueService).findAll(pageable);
    }

    @Test
    public void admin_success_add_venue_picture_is_null()  throws Exception{
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());
        when(venueService.create(any())).thenReturn(1);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
        verify(venueService).create(any());
    }

    @Test
    public void admin_success_add_venue_picture_is_not_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());
        when(venueService.create(any())).thenReturn(1);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
        verify(venueService).create(any());
    }

    @Test
    public void admin_fail_add_venue_picture_is_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());
        when(venueService.create(any())).thenReturn(0);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_add"));
        verify(venueService).create(any());
    }

    @Test
    public void admin_fail_add_venue_picture_is_not_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());
        when(venueService.create(any())).thenReturn(0);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/addVenue.do")
                        .file(mockMultipartFile).param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_add"));
        verify(venueService).create(any());
    }

    @Test
    public void admin_modify_venue_picture_is_null() throws Exception {
        when(venueService.findByVenueID(anyInt())).thenReturn(new Venue());

        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/modifyVenue.do")
                        .file(mockMultipartFile).param("venueID","1").param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
        verify(venueService).findByVenueID(anyInt());
        verify(venueService).update(any());
    }

    @Test
    public void admin_modify_venue_picture_is_not_null() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());
        when(venueService.findByVenueID(anyInt())).thenReturn(new Venue());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/modifyVenue.do")
                        .file(mockMultipartFile).param("venueID","1").param("venueName","venue").param("address","this is address")
                        .param("description","this is description").param("price","100")
                        .param("open_time","08:00").param("close_time","18:00");

        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("venue_manage"));
        verify(venueService).findByVenueID(anyInt());
        verify(venueService).update(any());
    }

    @Test
    public void admin_del_venue() throws Exception {

        ResultActions perform=mockMvc.perform(post("/delVenue.do").param("venueID","1"));
        perform.andExpect(status().isOk());
        verify(venueService).delById(anyInt());
    }

    @Test
    public  void return_already_exist_same_venue_name()  throws Exception{
        when(venueService.countVenueName("venue")).thenReturn(1);
        ResultActions perform=mockMvc.perform(post("/checkVenueName.do").param("venueName","venue"));
        perform.andExpect(status().isOk()).andExpect(content().string("false"));
        verify(venueService).countVenueName("venue");
    }

    @Test
    public  void return_not_exist_same_venue_name()  throws Exception{
        when(venueService.countVenueName("venue")).thenReturn(0);
        ResultActions perform=mockMvc.perform(post("/checkVenueName.do").param("venueName","venue"));
        perform.andExpect(status().isOk()).andExpect(content().string("true"));
        verify(venueService).countVenueName("venue");
    }
}
package com.meethere.controller.user;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VenueController.class)
class VenueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VenueService venueService;

    @Test
    public void return_venue_detail_html() throws Exception{
        when(venueService.findByVenueID(anyInt())).thenReturn(new Venue());
        ResultActions perform=mockMvc.perform(get("/venue").param("venueID","1"));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue");
        verify(venueService).findByVenueID(anyInt());

    }

    @Test
    public void return_venue_list_paged() throws Exception{
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
        Pageable venue_pageable= PageRequest.of(0,5, Sort.by("venueID").ascending());
        when(venueService.findAll(venue_pageable)).thenReturn(new PageImpl<>(venues,venue_pageable,1));
        ResultActions perform=mockMvc.perform(get("/venuelist/getVenueList").param("page","1"));
        perform.andExpect(status().isOk());
        verify(venueService).findAll(venue_pageable);
    }

    @Test
    public void return_venue_list_html() throws Exception{
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
        Pageable venue_pageable= PageRequest.of(0,5, Sort.by("venueID").ascending());

        when(venueService.findAll(venue_pageable)).thenReturn(new PageImpl<>(venues,venue_pageable,1));
        ResultActions perform=mockMvc.perform(get("/venue_list").param("page","1"));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue_list");
        assertModelAttributeAvailable(mv,"total");

        verify(venueService,times(2)).findAll(venue_pageable);
    }
}
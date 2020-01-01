package com.meethere.controller.user;

import com.meethere.controller.admin.AdminVenueController;
import com.meethere.entity.Order;
import com.meethere.entity.User;
import com.meethere.entity.Venue;
import com.meethere.entity.vo.OrderVo;
import com.meethere.service.OrderService;
import com.meethere.service.OrderVoService;
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
import org.springframework.web.util.NestedServletException;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderVoService orderVoService;
    @MockBean
    private VenueService venueService;

    @Test
    public void fail_return_order_manage_html_when_user_not_login() {
        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/order_manage")),"请登录！");
    }
    @Test
    public void success_return_order_manage_html_when_user_login() throws Exception {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        int orderID=1;
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Pageable order_pageable = PageRequest.of(0,5, Sort.by("orderTime").descending());
        Order order=new Order(orderID,userID,venueID,state,order_time,start_time,hours,total);
        Order order1=new Order(2,userID,venueID,state,order_time,start_time.plusHours(4),hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        when(orderService.findUserOrder(userID,order_pageable)).thenReturn(new PageImpl<>(orders,order_pageable,1));

        ResultActions perform=mockMvc.perform(get("/order_manage").sessionAttr("user",user));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");
        verify(orderService).findUserOrder(userID,order_pageable);

    }

    @Test
    public void return_order_place_html_by_click_from_venue_detail()throws Exception {
        when(venueService.findByVenueID(anyInt())).thenReturn(new Venue());
        ResultActions perform=mockMvc.perform(get("/order_place.do").param("venueID","1"));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue");
        verify(venueService).findByVenueID(anyInt());
    }

    @Test
    public void return_order_place_html_by_click_from_top()throws Exception {
        mockMvc.perform(get("/order_place")).andExpect(status().isOk());
    }

    @Test
    public void fail_return_order_list_when_user_not_login() throws Exception{
        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/getOrderList.do")),"请登录！");
    }

    @Test
    public void success_return_order_list_when_user_login() throws Exception{
        int id=1;
        String userID="user";
        User user=new User();
        user.setUserID("user");

        int orderID=1;
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Pageable order_pageable = PageRequest.of(0,5, Sort.by("orderTime").descending());
        Order order=new Order(orderID,userID,venueID,state,order_time,start_time,hours,total);
        Order order1=new Order(2,userID,venueID,state,order_time,start_time.plusHours(4),hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        when(orderService.findUserOrder(userID,order_pageable)).thenReturn(new PageImpl<>(orders,order_pageable,1));
        when(orderVoService.returnVo(any())).thenReturn(null);
        ResultActions perform=mockMvc.perform(get("/getOrderList.do").sessionAttr("user",user).param("page","1"));
        perform.andExpect(status().isOk());
        verify(orderService).findUserOrder(userID,order_pageable);
        verify(orderVoService).returnVo(any());
    }

    @Test
    public void fail_add_new_order_when_user_not_login()throws Exception {
        assertThrows(NestedServletException.class,()->mockMvc.perform(post("/addOrder.do").param("venueName","venue")
                .param("date","").param("startTime","2019-12-22 11:00").param("hours","1")),
                "请登录！");
    }

    @Test
    public void success_add_new_order_when_user_login() throws Exception{
        User user=new User();
        user.setUserID("user");
        ResultActions perform=mockMvc.perform(post("/addOrder.do").sessionAttr("user",user)
                                    .param("venueName","venue").param("date","").param("startTime","2019-12-22 11:00")
                                    .param("hours","1"));
        perform.andExpect(redirectedUrl("order_manage"));
        verify(orderService).submit(anyString(),any(),anyInt(),anyString());
    }

    @Test
    public void user_finish_order() throws Exception{
        ResultActions perform=mockMvc.perform(post("/finishOrder.do").param("orderID","1"));
        perform.andExpect(status().isOk());
        verify(orderService).finishOrder(anyInt());
    }

    @Test
    public void return_modify_order_html() throws Exception{
        when(orderService.findById(anyInt())).thenReturn(new Order());
        when(venueService.findByVenueID(anyInt())).thenReturn(new Venue());
        ResultActions perform=mockMvc.perform(get("/modifyOrder.do").param("orderID","1"));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"venue");
        assertModelAttributeAvailable(mv,"order");
    }

    @Test
    public void fail_modify_old_order_when_user_not_login() throws Exception{
        assertThrows(NestedServletException.class,()->mockMvc.perform(post("/modifyOrder")
                .param("venueName","venue").param("date","").param("startTime","2019-12-22 11:00")
                .param("hours","1").param("orderID","1")),
                "请登录！");

    }

    @Test
    public void success_modify_old_order_when_user_login()throws Exception {
        User user=new User();
        user.setUserID("user");
        ResultActions perform=mockMvc.perform(post("/modifyOrder").sessionAttr("user",user)
                .param("venueName","venue").param("date","").param("startTime","2019-12-22 11:00")
                .param("hours","1").param("orderID","1"));
        perform.andExpect(redirectedUrl("order_manage"));

    }
    @Test
    public void user_del_order()throws Exception {
        mockMvc.perform(post("/delOrder.do").param("orderID","1")).andExpect(status().isOk()).andExpect(content().string("true"));
        verify(orderService).delOrder(anyInt());
    }

    @Test
    public void return_ordered_list_on_someday()throws Exception {
        when(venueService.findByVenueName(anyString())).thenReturn(new Venue());
        when(orderService.findDateOrder(anyInt(),any(),any())).thenReturn(null);
        mockMvc.perform(get("/order/getOrderList.do").param("venueName","venue").param("date","2019-12-22")).andExpect(status().isOk());
        verify(venueService).findByVenueName(anyString());
        verify(orderService).findDateOrder(anyInt(),any(),any());
    }
}
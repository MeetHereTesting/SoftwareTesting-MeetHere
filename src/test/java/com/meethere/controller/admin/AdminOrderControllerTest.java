package com.meethere.controller.admin;

import com.meethere.entity.Order;
import com.meethere.service.OrderService;
import com.meethere.service.OrderVoService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminOrderController.class)
class AdminOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderVoService orderVoService;

    @Test
    public void return_reservation_manage_html() throws Exception {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;
        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        Pageable order_pageable= PageRequest.of(0,10, Sort.by("orderTime").descending());

        when(orderService.findAuditOrder()).thenReturn(orders);
        when(orderVoService.returnVo(orders)).thenReturn(null);
        when(orderService.findNoAuditOrder(any())).thenReturn(new PageImpl<>(orders,order_pageable,1));
        ResultActions perform=mockMvc.perform(get("/reservation_manage"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"order_list");
        assertModelAttributeAvailable(mv,"total");
        verify(orderService).findAuditOrder();
        verify(orderService).findNoAuditOrder(any());
        verify(orderVoService).returnVo(orders);
    }

    @Test
    public void admin_get_no_audit_order_list() throws Exception {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;
        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        Pageable order_pageable= PageRequest.of(0,10, Sort.by("orderTime").descending());

        when(orderVoService.returnVo(orders)).thenReturn(null);
        when(orderService.findNoAuditOrder(any())).thenReturn(new PageImpl<>(orders,order_pageable,1));
        ResultActions perform=mockMvc.perform(get("/admin/getOrderList.do").param("page","1"));
        perform.andExpect(status().isOk());
        verify(orderService).findNoAuditOrder(any());
        verify(orderVoService).returnVo(orders);
    }

    @Test
    public void admin_confirm_order() throws Exception {
        ResultActions perform=mockMvc.perform(post("/passOrder.do").param("orderID","1"));
        perform.andExpect(status().isOk());
        verify(orderService).confirmOrder(1);
    }

    @Test
    public void admin_reject_order() throws Exception {
        ResultActions perform=mockMvc.perform(post("/rejectOrder.do").param("orderID","1"));
        perform.andExpect(status().isOk());
        verify(orderService).rejectOrder(1);
    }
}
package com.meethere.controller.admin;

import com.meethere.entity.Order;
import com.meethere.entity.vo.OrderVo;
import com.meethere.service.OrderService;
import com.meethere.service.OrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderVoService orderVoService;

    @GetMapping("/reservation_manage")
    public String reservation_manage(Model model){
        List<Order> orders= orderService.findAuditOrder();
        List<OrderVo> orderVos=orderVoService.returnVo(orders);
        Pageable order_pageable= PageRequest.of(0,10, Sort.by("order_time").descending());
        model.addAttribute("order_list",orderVos);
        model.addAttribute("total",orderService.findNoAuditOrder(order_pageable).getTotalPages());

        return "admin/reservation_manage";
    }

    @GetMapping("/admin/getOrderList")
    @ResponseBody
    public List<OrderVo> getNoAuditOrder(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable order_pageable= PageRequest.of(page-1,10, Sort.by("order_time").descending());
        List<Order> orders=orderService.findNoAuditOrder(order_pageable).getContent();
        return orderVoService.returnVo(orders);
    }

    @PostMapping("/confirmOrder.do")
    @ResponseBody
    public void confirmOrder(int orderID) {
        orderService.confirmOrder(orderID);
    }

    @PostMapping("/rejectOrder.do")
    @ResponseBody
    public void rejectOrder(int orderID) {
        orderService.rejectOrder(orderID);
    }
}

package com.meethere.controller.user;

import com.meethere.entity.News;
import com.meethere.entity.Order;
import com.meethere.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/order_manage")
    public String order_manage(Model model,HttpServletRequest request){
        Pageable order_pageable = PageRequest.of(0,5, Sort.by("orderID").descending());
        Page<Order> page=orderService.findUserOrder(request,order_pageable);
        model.addAttribute("order_list",page);

        return "order_manage";
    }

    @GetMapping("/order_place")
    public String order_place(Model model) {
        return "order_place";
    }

    @GetMapping("/order_list.do")
    @ResponseBody
    public Page<Order> news_list(@RequestParam(value = "page",defaultValue = "1")int page,HttpServletRequest request){
        Pageable order_pageable = PageRequest.of(page-1,5, Sort.by("orderID").descending());
        Page<Order> page1=orderService.findUserOrder(request,order_pageable);
        return page1;
    }

    @GetMapping("/addOrder")
    public void addOrder(int venueID){

    }



}

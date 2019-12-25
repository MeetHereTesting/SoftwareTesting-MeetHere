package com.meethere.controller.user;

import com.meethere.entity.News;
import com.meethere.entity.Order;
import com.meethere.entity.Venue;
import com.meethere.entity.vo.OrderVo;
import com.meethere.entity.vo.VenueOrder;
import com.meethere.service.OrderService;
import com.meethere.service.OrderVoService;
import com.meethere.service.VenueService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderVoService orderVoService;
    @Autowired
    private VenueService venueService;

    @GetMapping("/order_manage")
    public String order_manage(Model model,HttpServletRequest request){
        Pageable order_pageable = PageRequest.of(0,5, Sort.by("orderTime").descending());
        Page<Order> page=orderService.findUserOrder(request,order_pageable);
        model.addAttribute("total",page.getTotalPages());
        return "order_manage";
    }

    @GetMapping("/order_place.do")
    public String order_place(Model model,int venueID) {

        Venue venue=venueService.findByVenueID(venueID);
        model.addAttribute("venue",venue);
        return "order_place";
    }

    @GetMapping("/order_place")
    public String order_place(Model model) {
        return "order_place";
    }

    @GetMapping("/getOrderList.do")
    @ResponseBody
    public List<OrderVo> news_list(@RequestParam(value = "page",defaultValue = "1")int page, HttpServletRequest request){
        Pageable order_pageable = PageRequest.of(page-1,5, Sort.by("orderTime").descending());
        Page<Order> page1=orderService.findUserOrder(request,order_pageable);
        return orderVoService.returnVo(page1.getContent());
    }

    @GetMapping("/addOrder")
    public void addOrder(int venueID){

    }

    @PostMapping("/finishOrder.do")
    @ResponseBody
    public void finishOrder(int orderID) {
        orderService.finishOrder(orderID);
    }

    @GetMapping("/modifyOrder.do")
    public String modifyOrder(Model model,int orderID){
        Order order=orderService.findById(orderID);
        Venue venue=venueService.findByVenueID(order.getVenueID());
        model.addAttribute("venue",venue);
        return "order_place";
    }

    @PostMapping("/delOrder.do")
    @ResponseBody
    public boolean delOrder(int orderID) {
        orderService.finishOrder(orderID);
        return true;
    }

    @GetMapping("/order/getOrderList.do")
    @ResponseBody
    public VenueOrder modifyOrder(String venueName,String date){
        Venue venue=venueService.findByVenueName(venueName);
        VenueOrder venueOrder=new VenueOrder();
        date=date+" 00:00:00";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        LocalDateTime ldt = LocalDateTime.parse(date,df);
        LocalDateTime ldt2=ldt.plusDays(1);
        System.out.println(ldt);
        System.out.println(ldt2);

        venueOrder.setVenue(venue);
        venueOrder.setOrders(orderService.findDateOrder(venue.getVenueID(),ldt,ldt2));
        System.out.println(venueOrder);
        return venueOrder;

    }
}

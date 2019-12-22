package com.meethere.controller.admin;

import com.meethere.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminOrderController {
    @Autowired
    private OrderService orderService;


}

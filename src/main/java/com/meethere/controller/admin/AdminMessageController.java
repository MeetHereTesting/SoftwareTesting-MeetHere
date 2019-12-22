package com.meethere.controller.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AdminMessageController {

    @GetMapping("/order_manage")
    public String order_manage(Model model){
        return "order_manage";
    }
}

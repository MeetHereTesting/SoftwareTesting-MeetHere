package com.meethere.controller.admin;

import com.meethere.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminGymController {
    @Autowired
    private VenueService venueService;

    @RequestMapping("/manage")
    public String toAdd(){
        return "maneg";
    }

}

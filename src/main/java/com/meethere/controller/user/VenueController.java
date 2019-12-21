package com.meethere.controller.user;

import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/venue")
public class VenueController {
    @Autowired
    private VenueService venueService;

    /**
     * 场馆详情页面
     */
    @RequestMapping("/get.html")
    public String toVenuePage(Model model,int gymID){
        Venue venue = venueService.findByVenueID(gymID);
        model.addAttribute("venue", venue);
        return "vuene.html";
    }



}

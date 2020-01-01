package com.meethere.controller.user;

import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class VenueController {
    @Autowired
    private VenueService venueService;

    /**
     * 场馆详情页面
     */
    @GetMapping("/venue")
    public String toGymPage(Model model,int venueID){
        Venue venue = venueService.findByVenueID(venueID);
        model.addAttribute("venue", venue);
        return "venue";
    }

    /**
     * 分页查看场馆
     *@param page
     */
    @ResponseBody
    @GetMapping("/venuelist/getVenueList")
    public Page<Venue> venue_list(@RequestParam(value = "page",defaultValue = "1")int page){
        System.out.println("success");
        Pageable venue_pageable= PageRequest.of(page-1,5, Sort.by("venueID").ascending());
        return venueService.findAll(venue_pageable);
    }

    @GetMapping("/venue_list")
    public String venue_list(Model model){
        Pageable venue_pageable= PageRequest.of(0,5, Sort.by("venueID").ascending());
        List<Venue> venue_list=venueService.findAll(venue_pageable).getContent();
        model.addAttribute("venue_list",venue_list);
        model.addAttribute("total", venueService.findAll(venue_pageable).getTotalPages());
        return "venue_list";
    }

}

package com.meethere.controller.admin;

import antlr.StringUtils;
import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import com.meethere.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AdminVenueController {
    @Autowired
    private VenueService venueService;


    @RequestMapping("/venue_manage")
    public String venue_manage(Model model){
        Pageable pageable= PageRequest.of(0,10, Sort.by("venueID").ascending());
        model.addAttribute("total",venueService.findAll(pageable).getTotalPages());
        return "admin/venue_manage";
    }

    @RequestMapping("/venue_edit")
    public String editVenue(Model model,int venueID){
        Venue venue=venueService.findByVenueID(venueID);
        model.addAttribute("venue",venue);
        return "/admin/venue_edit";
    }

    @RequestMapping("/venue_add")
    public String venue_add(){
        return "/admin/venue_add";
    }

    @GetMapping("/venueList.do")
    @ResponseBody
    public List<Venue> getVenueList(@RequestParam(value = "page",defaultValue = "1")int page){
        Pageable pageable= PageRequest.of(page-1,10, Sort.by("venueID").ascending());
        return venueService.findAll(pageable).getContent();

    }

    @PostMapping("/addVenue.do")
    @ResponseBody
    public void addVenue(String venueName, String address, String description,
                         int price, MultipartFile picture, String open_time,String close_time,HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        Venue venue=new Venue();
        venue.setVenueName(venueName);
        venue.setAddress(address);
        venue.setDescription(description);
        venue.setPrice(price);
        venue.setOpen_time(open_time);
        venue.setClose_time(close_time);

        if(!Objects.equals(picture.getOriginalFilename(), "")){
            venue.setPicture(FileUtil.saveVenueFile(picture));
        }else{
            venue.setPicture("");
        }

        int id=venueService.create(venue);
        if (id <= 0) {
            request.setAttribute("message", "添加失败！");
            response.sendRedirect("venue_add");
        } else {
            response.sendRedirect("venue_manage");
        }
    }

    @PostMapping("/modifyVenue.do")
    @ResponseBody
    public void modifyVenue(int venueID,String venueName, String address, String description,
                            int price, MultipartFile picture, String open_time,String close_time,HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        Venue venue=venueService.findByVenueID(venueID);
        venue.setVenueName(venueName);
        venue.setAddress(address);
        venue.setDescription(description);
        venue.setPrice(price);
        if(!Objects.equals(picture.getOriginalFilename(), "")){
            venue.setPicture(FileUtil.saveVenueFile(picture));
        }
        venue.setOpen_time(open_time);
        venue.setClose_time(close_time);
        venueService.update(venue);
        response.sendRedirect("venue_manage");
    }

    @PostMapping("/delVenue.do")
    @ResponseBody
    public boolean delVenue(int venueID) throws IOException {
        venueService.delById(venueID);
        return true;
    }

    @PostMapping("/checkVenueName.do")
    @ResponseBody
    public boolean checkVenueName(String venueName){
        int count=venueService.countVenueName(venueName);
        return count < 1;
    }

}

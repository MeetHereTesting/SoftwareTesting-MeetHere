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
import java.util.List;

@Controller
public class AdminVenueController {
    @Autowired
    private VenueService venueService;


    @GetMapping("/venue_manage")
    public String venue_manage(Model model){
        Pageable pageable= PageRequest.of(0,10, Sort.by("venueID").ascending());
        model.addAttribute("total",venueService.findAll(pageable).getTotalPages());
        return "admin/venue_manage";
    }

    @RequestMapping("/admin/venue_edit")
    public String editVenue(Model model,int venueID){
        Venue venue=venueService.findByVenueID(venueID);
        model.addAttribute("venue",venue);
        return "/admin/venue_edit";
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
                         int price, MultipartFile image, String open_time,String close_time,HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        Venue venue=new Venue();
        venue.setVenueName(venueName);
        venue.setAddress(address);
        venue.setDescription(description);
        venue.setPrice(price);
        String imgUrl = FileUtil.saveFile(image);
        venue.setPicture(imgUrl);
        int id = venueService.create(venue);
        if (id <= 0) {
            request.setAttribute("message", "添加失败！");
            request.getRequestDispatcher(".html").forward(request, response);
        } else {
            request.getRequestDispatcher("toEdit.html?id=" + id).forward(request, response);
        }
    }

    @PostMapping("/modifyVenue.do")
    public void modifyVenue(int venueID, String venueName, String address, String description,
                            int price, MultipartFile image, String open_time,String close_time,HttpServletRequest request,
                            HttpServletResponse response){
        Venue venue=venueService.findByVenueID(venueID);
        venue.setVenueName(venueName);
        venue.setAddress(address);
        venue.setDescription(description);
        venue.setPrice(price);
//        if (StringUtils.isNotBlank(imgUrl)) {
//            product.setImage(imgUrl);
//        }
//        boolean flag = false;
//        try {
//            productService.update(product);
//            flag = true;
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//        if (!flag) {
//            request.setAttribute("message", "更新失败！");
//        }
//        response.sendRedirect("toList.html");
//
    }

}

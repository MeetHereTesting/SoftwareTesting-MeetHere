package com.meethere.IntegrationTest.Service;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import com.mysql.cj.log.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@Transactional
public class VenueServiceTest {
    @Autowired
    private VenueService venueService;

    @Test
    void find_venue_by_venueID() {
        int venueID=2;
        Venue res=venueService.findByVenueID(venueID);
        assertEquals(venueID,res.getVenueID());
    }

    @Test
    void  find_venue_by_venueName() {
        int venueID=2;
        String venue_name="2222";
        Venue res=venueService.findByVenueName(venue_name);
        assertEquals(venueID,res.getVenueID());
        assertEquals(venue_name,res.getVenueName());

    }

    @Test
    void find_all_return_page() {
        Pageable pageable= PageRequest.of(0,10);
        venueService.findAll(pageable);
    }

    @Test
    void find_all_return_list() {
        List<Venue> res=venueService.findAll();
    }

    @Test
    void create_venue() {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);
        int res=venueService.create(venue);
        assertTrue(res>0);

    }

    @Test
    void update_venue() {
        int venueID=2;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        venueService.update(venue);

    }

    @Test
    void del_venue_by_venueID() {
        venueService.delById(2);

    }

    @Test
    void count_number_of_same_venueName() {
        String venueName="222";

        int res1=venueService.countVenueName(venueName);
        assertEquals(1,res1);
        int res2=venueService.countVenueName("");
        assertEquals(0,res2);

    }
}

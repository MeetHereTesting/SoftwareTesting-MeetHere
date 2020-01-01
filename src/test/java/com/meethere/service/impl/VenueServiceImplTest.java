package com.meethere.service.impl;

import com.meethere.MeetHereApplication;
import com.meethere.dao.VenueDao;
import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
class VenueServiceImplTest {
    @Mock
    private VenueDao venueDao;
    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    void find_venue_by_venueID() {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        when(venueDao.getOne(venueID)).thenReturn(venue);
        Venue res=venueService.findByVenueID(venueID);
        assertAll("test find venue by venueID",()->assertEquals(venueID,res.getVenueID()),
                ()->assertEquals(venue_name,res.getVenueName()),
                ()->assertEquals(description,res.getDescription()),
                ()->assertEquals(price,res.getPrice()),
                ()->assertEquals(picture,res.getPicture()),
                ()->assertEquals(address,res.getAddress()),
                ()->assertEquals(open_time,res.getOpen_time()),
                ()->assertEquals(close_time,res.getClose_time()));
        verify(venueDao).getOne(venueID);
    }

    @Test
    void  find_venue_by_venueName() {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        when(venueDao.findByVenueName(venue_name)).thenReturn(venue);
        Venue res=venueService.findByVenueName(venue_name);
        assertAll("test find venue by venueID",()->assertEquals(venueID,res.getVenueID()),
                ()->assertEquals(venue_name,res.getVenueName()),
                ()->assertEquals(description,res.getDescription()),
                ()->assertEquals(price,res.getPrice()),
                ()->assertEquals(picture,res.getPicture()),
                ()->assertEquals(address,res.getAddress()),
                ()->assertEquals(open_time,res.getOpen_time()),
                ()->assertEquals(close_time,res.getClose_time()));
        verify(venueDao).findByVenueName(venue_name);
    }

    @Test
    void find_all_return_page() {
        Pageable pageable= PageRequest.of(0,10);
        when(venueDao.findAll(pageable)).thenReturn(null);
        venueService.findAll(pageable);
        verify(venueDao).findAll(pageable);
    }

    @Test
    void find_all_return_list() {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);
        List<Venue> venues=new ArrayList<>();
        venues.add(venue);

        when(venueDao.findAll()).thenReturn(venues);
        List<Venue> res=venueService.findAll();
        assertEquals(1,res.size());
        verify(venueDao).findAll();
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

        when(venueDao.save(venue)).thenReturn(venue);
        assertEquals(1,venueService.create(venue));
        verify(venueDao).save(any());
    }

    @Test
    void update_venue() {
        int venueID=1;
        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        when(venueDao.save(any())).thenReturn(null);
        venueService.update(any());
        verify(venueDao).save(any());
    }

    @Test
    void del_venue_by_venueID() {
        venueService.delById(1);
        verify(venueDao).deleteById(1);

        venueService.delById(2);
        verify(venueDao).deleteById(2);

        verify(venueDao,times(2)).deleteById(anyInt());
    }

    @Test
    void count_number_of_same_venueName() {
        String venueName="venue";
        when(venueDao.countByVenueName(venueName)).thenReturn(1).thenReturn(2);
        int res1=venueService.countVenueName(venueName);
        assertEquals(1,res1);
        int res2=venueService.countVenueName(venueName);
        assertEquals(2,res2);
        verify(venueDao,times(2)).countByVenueName(venueName);
    }
}
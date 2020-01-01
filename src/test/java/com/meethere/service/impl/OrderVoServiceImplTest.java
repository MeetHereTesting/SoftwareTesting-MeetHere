package com.meethere.service.impl;

import com.meethere.MeetHereApplication;
import com.meethere.dao.OrderDao;
import com.meethere.dao.VenueDao;
import com.meethere.entity.Order;
import com.meethere.entity.Venue;
import com.meethere.entity.vo.OrderVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
class OrderVoServiceImplTest {
    @Mock
    private VenueDao venueDao;
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderVoServiceImpl orderVoService;

    @Test
    public void return_orderVo_by_orderID() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);

        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        when(orderDao.findByOrderID(orderID)).thenReturn(order);
        when(venueDao.findByVenueID(venueID)).thenReturn(venue);
        OrderVo res=orderVoService.returnOrderVoByOrderID(orderID);
        assertAll(()->assertEquals(orderID,res.getOrderID()),()->assertEquals(venueID,res.getVenueID()),()->assertEquals(venue_name,res.getVenueName()),
                ()->assertEquals(user,res.getUserID()),()->assertEquals(state,res.getState()),()->assertEquals(order_time,res.getOrderTime()),
                ()->assertEquals(start_time,res.getStartTime()),()->assertEquals(hours,res.getHours()),()->assertEquals(total,res.getTotal()));
        verify(orderDao).findByOrderID(orderID);
        verify(venueDao).findByVenueID(venueID);
    }

    @Test
    public void return_orderVo_list_by_order_list() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);

        String venue_name="venue";
        String description="this is description";
        int price=100;
        String picture="";
        String address="address";
        String open_time="08:00";
        String close_time="18:00";
        Venue venue=new Venue(venueID,venue_name,description,price,picture,address,open_time,close_time);

        when(orderDao.findByOrderID(orderID)).thenReturn(order);
        when(venueDao.findByVenueID(venueID)).thenReturn(venue);
        List<OrderVo> res=orderVoService.returnVo(orders);
        assertEquals(1,res.size());
        verify(orderDao).findByOrderID(orderID);
        verify(venueDao).findByVenueID(venueID);
    }
}
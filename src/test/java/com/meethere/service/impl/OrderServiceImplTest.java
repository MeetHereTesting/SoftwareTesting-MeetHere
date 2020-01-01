package com.meethere.service.impl;

import com.meethere.MeetHereApplication;
import com.meethere.dao.OrderDao;
import com.meethere.dao.VenueDao;
import com.meethere.entity.Order;
import com.meethere.entity.Venue;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
class OrderServiceImplTest {
    @Mock
    private VenueDao venueDao;
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void find_order_by_orderID() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        when(orderDao.getOne(orderID)).thenReturn(order);

        Order res=orderService.findById(orderID);
        assertAll("test find order by ID",()->assertEquals(orderID,res.getOrderID()),
                ()->assertEquals(user,res.getUserID()),
                ()->assertEquals(venueID,res.getVenueID()),
                ()->assertEquals(order_time,res.getOrderTime()),
                ()->assertEquals(start_time,res.getStartTime()),
                ()->assertEquals(hours,res.getHours()),
                ()->assertEquals(state,res.getState()),
                ()->assertEquals(total,res.getTotal()));
        verify(orderDao).getOne(orderID);
    }


    @Test
    void find_order_list_on_someday() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        Order order1=new Order(2,user,venueID,state,order_time,start_time.plusHours(4),hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        orders.add(order1);

        when(orderDao.findByVenueIDAndStartTimeIsBetween(venueID,start_time,start_time.plusDays(1))).thenReturn(orders);
        List<Order> res=orderService.findDateOrder(venueID,start_time,start_time.plusDays(1));

        assertEquals(2,res.size());
        verify(orderDao).findByVenueIDAndStartTimeIsBetween(venueID,start_time,start_time.plusDays(1));
    }

    @Test
    void find_user_orders() {
        String user="user";
        Pageable pageable=PageRequest.of(0,10);
        when(orderDao.findAllByUserID(user,pageable)).thenReturn(null);
        orderService.findUserOrder(user,pageable);
        verify(orderDao).findAllByUserID(user,pageable);
    }


    @Test
    void update_order() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        Venue venue=new Venue(venueID,"venueName","description",100,"","address","08:00","17:00");
        when(venueDao.findByVenueName("venueName")).thenReturn(venue);
        when(orderDao.findByOrderID(orderID)).thenReturn(order);
        when(orderDao.save(order)).thenReturn(null);

        orderService.updateOrder(orderID,"venueName",start_time,hours,user);
        verify(orderDao).findByOrderID(orderID);
        verify(orderDao).save(order);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    void submit_a_new_order() {
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(0,user,venueID,state,order_time,start_time,hours,total);
        Venue venue=new Venue(venueID,"venueName","description",100,"","address","08:00","17:00");
        when(venueDao.findByVenueName("venueName")).thenReturn(venue);
        when(orderDao.save(order)).thenReturn(null);

        orderService.submit("venueName",start_time,hours,user);
        verify(orderDao).save(any());
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    void del_order() {
        orderService.delOrder(1);
        verify(orderDao).deleteById(1);

        orderService.delOrder(2);
        verify(orderDao).deleteById(2);

        verify(orderDao,times(2)).deleteById(anyInt());
    }

    @Test
    void confirm_order_success() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        when(orderDao.findByOrderID(orderID)).thenReturn(order);

        orderService.confirmOrder(orderID);
        verify(orderDao).findByOrderID(orderID);
        verify(orderDao).updateState(2,orderID);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void confirm_order_fail(){
        int orderID=1;
        when(orderDao.findByOrderID(orderID)).thenReturn(null);
        assertThrows(RuntimeException.class,
                ()->orderService.confirmOrder(orderID),
                "订单不存在"    );
    }
    @Test
    void finish_order_success() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        when(orderDao.findByOrderID(orderID)).thenReturn(order);

        orderService.finishOrder(orderID);
        verify(orderDao).findByOrderID(orderID);
        verify(orderDao).updateState(3,orderID);
        verifyNoMoreInteractions(orderDao);
    }
    @Test
    public void finish_order_fail(){
        int orderID=1;
        when(orderDao.findByOrderID(orderID)).thenReturn(null);
        assertThrows(RuntimeException.class,
                ()->orderService.finishOrder(orderID),
                "订单不存在"    );
    }

    @Test
    void reject_order_success() {
        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=1;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        when(orderDao.findByOrderID(orderID)).thenReturn(order);

        orderService.rejectOrder(orderID);
        verify(orderDao).findByOrderID(orderID);
        verify(orderDao).updateState(4,orderID);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void  reject_order_fail(){
        int orderID=1;
        when(orderDao.findByOrderID(orderID)).thenReturn(null);
        assertThrows(RuntimeException.class,
                ()->orderService.rejectOrder(orderID),
                "订单不存在"    );
    }
    @Test
    void return_noAudit_order_paged() {
        int state=1;
        Pageable pageable=PageRequest.of(0,10);
        when(orderDao.findAllByState(state,pageable)).thenReturn(null);
        orderService.findNoAuditOrder(pageable);
        verify(orderDao).findAllByState(state,pageable);
    }

    @Test
    void  return_audit_order_paged() {
        int state1=2,state2=3;

        int orderID=1;
        String user="user";
        int venueID=2;
        LocalDateTime order_time=LocalDateTime.now();
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;
        int state=2;
        int total=300;

        Order order=new Order(orderID,user,venueID,state,order_time,start_time,hours,total);
        Order order1=new Order(2,user,venueID,state,order_time,start_time.plusHours(4),hours,total);
        List<Order> orders=new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        when(orderDao.findAudit(state1,state2)).thenReturn(orders);

        List<Order> res=orderService.findAuditOrder();
        assertEquals(2,res.size());
        verify(orderDao).findAudit(state1,state2);
    }
}
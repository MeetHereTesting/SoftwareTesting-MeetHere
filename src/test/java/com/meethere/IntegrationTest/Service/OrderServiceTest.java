package com.meethere.IntegrationTest.Service;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Order;
import com.meethere.entity.Venue;
import com.meethere.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Test
    void find_order_by_orderID() {
        int orderID=1;

        Order res=orderService.findById(orderID);
        assertEquals(orderID,res.getOrderID());
    }


    @Test
    void find_order_list_on_someday() {
        int venueID=2;
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);

        List<Order> res=orderService.findDateOrder(venueID,start_time,start_time.plusDays(1));
        assertEquals(0,res.size());

    }

    @Test
    void find_user_orders() {
        String user="user";
        Pageable pageable= PageRequest.of(0,10);

        List<Order> orders= orderService.findUserOrder(user,pageable).getContent();
        for(Order o:orders){
            assertEquals(user,o.getUserID());
        }
    }


    @Test
    void update_order() {
        int orderID=1;
        String user="user";
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;

        orderService.updateOrder(orderID,"222",start_time,hours,user);

    }

    @Test
    void submit_a_new_order() {
        String user="user";
        LocalDateTime start_time= LocalDateTime.now().plusDays(1);
        int hours=3;

        orderService.submit("222",start_time,hours,user);

    }

    @Test
    void del_order() {
        orderService.delOrder(1);

        orderService.delOrder(2);

    }

    @Test
    void confirm_order_success() {
        int orderID=1;
        orderService.confirmOrder(orderID);

    }

    @Test
    public void confirm_order_fail(){
        int orderID=100;
        assertThrows(RuntimeException.class,
                ()->orderService.confirmOrder(orderID),
                "订单不存在"    );
    }
    @Test
    void finish_order_success() {
        int orderID=1;
        orderService.finishOrder(orderID);

    }
    @Test
    public void finish_order_fail(){
        int orderID=100;
        assertThrows(RuntimeException.class,
                ()->orderService.finishOrder(orderID),
                "订单不存在"    );
    }

    @Test
    void reject_order_success() {
        int orderID=1;
        orderService.rejectOrder(orderID);
    }

    @Test
    public void  reject_order_fail(){
        int orderID=100;
        assertThrows(RuntimeException.class,
                ()->orderService.rejectOrder(orderID),
                "订单不存在"    );
    }
    @Test
    void return_noAudit_order_paged() {
        int state=1;
        Pageable pageable=PageRequest.of(0,10);
        List<Order> orders= orderService.findNoAuditOrder(pageable).getContent();
        for(Order o:orders){
            assertEquals(state,o.getState());
        }
    }

    @Test
    void  return_audit_order_paged() {
       orderService.findAuditOrder();
    }
}

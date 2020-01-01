package com.meethere.service;

import com.meethere.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {
    int STATE_NO_AUDIT=1;
    int STATE_WAIT=2;
    int STATE_FINISH=3;
    int STATE_REJECT=4;

    /**
     * 根据orderID查看订单
     *
     * @param OrderID
     * @return
     */
    Order findById(int OrderID);


    Page<Order> findNoAuditOrder(Pageable pageable);

    List<Order> findAuditOrder();


    List<Order> findDateOrder(int venueID, LocalDateTime startTime, LocalDateTime startTime2);

    Page<Order> findUserOrder(String userID,Pageable pageable);

    void updateOrder(int orderID,String venueName, LocalDateTime startTime, int hours, String userID);
    /**
     * 新建订单
     * @param venueName
     * @param startTime
     * @param hours
     */
    void submit(String venueName, LocalDateTime startTime, int hours, String userID);

    /**
     * 删除订单
     * @param orderID
     */
    void delOrder(int orderID);

    /**
     * 通过订单
     * @param orderID
     */
    void confirmOrder(int orderID);

    /**
     * 完成订单
     * @param orderID
     */
    void finishOrder(int orderID);

    /**
     *拒绝预定
     * @param orderID
     */
    void rejectOrder(int orderID);
}

package com.meethere.service.impl;

import com.meethere.dao.OrderDao;

import com.meethere.dao.VenueDao;
import com.meethere.entity.Venue;
import com.meethere.entity.Order;
import com.meethere.entity.User;
import com.meethere.service.OrderService;
import com.meethere.service.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private VenueDao venueDao;

    @Override
    public Order findById(int OrderID) {
        return orderDao.getOne(OrderID);
    }

    @Override
    public Page<Order> findAll(Pageable pageable, int state) {
        return null;
    }

    @Override
    public List<Order> findDateOrder(int venueID, LocalDateTime startTime, LocalDateTime startTime2) {
        return orderDao.findByVenueIDAndStartTimeIsBetween(venueID,startTime,startTime2);
    }

    @Override
    public Page<Order> findUserOrder(String userID, Pageable pageable) {
        return orderDao.findAllByUserID(userID,pageable);
    }

    @Override
    public void updateStates(int orderID, int state) {
        Order order=orderDao.getOne(orderID);
        order.setState(state);
        orderDao.save(order);
    }

    @Override
    public void updateOrder(int orderID, String venueName, LocalDateTime startTime, int hours,String userID)  {
        Venue venue =venueDao.findByVenueName(venueName);
        Order order=orderDao.findByOrderID(orderID);
        order.setState(STATE_NO_AUDIT);
        order.setHours(hours);
        order.setVenueID(venue.getVenueID());
        order.setOrderTime(LocalDateTime.now());
        order.setStartTime(startTime);
        order.setUserID(userID);
        order.setTotal(hours* venue.getPrice());

        orderDao.save(order);
    }

    @Override
    public void submit(String venueName, LocalDateTime startTime, int hours, String userID) {

        Venue venue =venueDao.findByVenueName(venueName);

        Order order=new Order();
        order.setState(STATE_NO_AUDIT);
        order.setHours(hours);
        order.setVenueID(venue.getVenueID());
        order.setOrderTime(LocalDateTime.now());
        order.setStartTime(startTime);
        order.setUserID(userID);
        order.setTotal(hours* venue.getPrice());
        orderDao.save(order);
    }

    @Override
    public void delOrder(int orderID) {
        orderDao.deleteById(orderID);
    }

    @Override
    public void confirmOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderDao.updateState(STATE_WAIT,order.getOrderID());
    }

    @Override
    public void finishOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderDao.updateState(STATE_FINISH,order.getOrderID());
    }

    @Override
    public void rejectOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderDao.updateState(STATE_REJECT,order.getOrderID());
    }

    @Override
    public Page<Order> findNoAuditOrder(Pageable pageable) {
        return orderDao.findAllByState(STATE_NO_AUDIT,pageable);
    }

    @Override
    public List<Order> findAuditOrder() {
        return orderDao.findAudit(STATE_WAIT,STATE_FINISH);
    }
}

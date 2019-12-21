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
    public Page<Order> findAll(Pageable pageable) {
        return orderDao.findAll(pageable);
    }

    @Override
    public List<Order> findUserOrder(HttpServletRequest httpRequest) {
        Object user=httpRequest.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录");
        User loginUser=(User)user;
        return orderDao.findByUserID(loginUser.getUserID());
    }

    @Override
    public void updateStates(int orderID, int state) {
        Order order=orderDao.getOne(orderID);
        order.setState(state);
        orderDao.save(order);
    }

    @Override
    public void submit(int gymID, Date startTime, int hours, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("请登录！");
        Venue venue =venueDao.findByVenueID(gymID);

        User loginUser = (User) user;
        Order order=new Order();
        order.setState(STATE_NO_AUDIT);
        order.setHours(hours);
        order.setVenueID(gymID);
        order.setOrderTime(new Date());
        order.setStartTime(startTime);
        order.setUserID(loginUser.getUserID());
        order.setTotal(hours* venue.getPrice());

        orderDao.save(order);
        response.sendRedirect("order.html");
    }

    @Override
    public void delOrder(int orderID) {
        orderDao.deleteById(orderID);
    }

    @Override
    public void confirmOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_WAIT,order.getOrderID());
    }

    @Override
    public void finishOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_FINISH,order.getOrderID());
    }

    @Override
    public void rejectOrder(int orderID) {
        Order order=orderDao.findByOrderID(orderID);
        if(order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_REJECT,order.getOrderID());
    }
}

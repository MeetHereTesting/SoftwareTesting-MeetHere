package com.meethere.service.impl;

import com.meethere.dao.OrderDao;
import com.meethere.entity.Order;
import com.meethere.entity.User;
import com.meethere.service.OrderService;
import com.meethere.service.exception.LoginException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

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
    public void submit(int gymID, Date startTime, int hours,HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("请登录！");
        User loginUser = (User) user;
        Order order=new Order();
        order.setState(1);
        order.setHours(hours);
        order.setGymID(gymID);
        order.setOrderTime(new Date());
        order.setStartTime(startTime);
        order.setUserID(loginUser.getUserID());
        orderDao.save(order);
    }



    @Override
    public void delOrder(int orderID) {
        orderDao.deleteById(orderID);
    }
}

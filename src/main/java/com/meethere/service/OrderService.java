package com.meethere.service;

import com.meethere.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface OrderService {
    int STATE_NO_AUDIT=1;
    int STATE_WAIT=2;
    int STATE_FINISH=3;
    int STATE_FAIL=4;
    /**
     * 根据orderID查看订单
     *
     * @param OrderID
     * @return
     */
    Order findById(int OrderID);

    /**
     * 分页查看所有订单
     * @param pageable
     * @return
     */
    Page<Order> findAll(Pageable pageable);

    /**
     * 查看用户的订单
     *
     * @param httpRequest
     * @return
     */
    List<Order> findUserOrder(HttpServletRequest httpRequest);

    /**
     * 更新订单状态
     *
     * @param orderID
     * @param state
     */
    void updateStates(int orderID,int state);

    /**
     * 新建订单
     * @param gymID
     * @param startTime
     * @param hours
     */
    void submit( int gymID,Date startTime,int hours,HttpServletRequest request);

    /**
     * 删除订单
     * @param orderID
     */
    void delOrder(int orderID);
}

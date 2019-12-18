package com.meethere.dao;

import com.meethere.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {
    List<Order> findByUserID(String userID);

    @Transactional
    @Modifying
    @Query(value="update Order o set o.state=?1 where o.orderID=?2",nativeQuery =true)
    void updateState(int state,int orderID);
}

package com.meethere.dao;

import com.meethere.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {

    Order findByOrderID(int orderID);

    Page<Order> findAllByState(int state,Pageable pageable);

    List<Order> findByVenueIDAndStartTimeIsBetween(int venueID, LocalDateTime startTime, LocalDateTime startTime2);

    @Query(value = "select * from `order` o where o.state = ?1 or o.state = ?2 ", nativeQuery = true)
    List<Order> findAudit(int state1,int state2);

    Page<Order> findAllByUserID(String userID, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="update `order` o set o.state=?1 where o.orderID=?2",nativeQuery =true)
    void updateState(int state, int orderID);
}

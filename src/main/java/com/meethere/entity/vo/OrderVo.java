package com.meethere.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    private int orderID;

    private String userID;

    private int venueID;

    private String venueName;

    /**
     * 1未审核 2已审核 3已完成 4失效
     */
    private int state;

    @Column(name="order_time")
    private LocalDateTime orderTime;

    @Column(name="start_time")
    private LocalDateTime startTime;

    private int hours;

    private int total;
}

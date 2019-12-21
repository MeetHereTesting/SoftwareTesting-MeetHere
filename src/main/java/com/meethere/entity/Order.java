package com.meethere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private int orderID;

    private String userID;

    private int venueID;

    /**
     * 1未审核 2已审核 3已完成 4失效
     */
    private int state;

    @Column(name="order_time")
    private Date orderTime;

    @Column(name="start_time")
    private Date startTime;

    private int hours;

    private int total;

}

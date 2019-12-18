package com.meethere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private int orderID;

    @Id
    private String userID;

    @Id
    private int gymID;

    /**
     * 1未审核 2已审核 3已完成 4失效
     */
    private int state;

    private Date orderTime;

    private Date startTime;

    private int hours;

}

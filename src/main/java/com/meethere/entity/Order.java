package com.meethere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    private String userID;

    private int venueID;

    /**
     * 1未审核 2已审核 3已完成 4失效
     */
    @Column(name="state")
    private int state;

    @Column(name="order_time")
    private LocalDateTime orderTime;

    @Column(name="start_time")
    private LocalDateTime startTime;

    private int hours;

    private int total;

}

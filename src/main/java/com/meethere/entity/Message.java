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
public class Message {

    @Id
    @GeneratedValue
    private int messageID;

    private String userID;

    private String content;

    private Date time;

    /**
     * 1未审核 2审核通过 3拒绝留言发表
     */
    private int state;
}

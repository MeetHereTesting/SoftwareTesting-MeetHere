package com.meethere.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo {

    private int messageID;

    private String userID;

    private String content;

    private Date time;

    private String userName;

    private String picture;
}

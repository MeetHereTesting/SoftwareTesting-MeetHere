package com.meethere.service;

import com.meethere.entity.vo.MessageVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageVoService  {
    MessageVo findByMessageID(int messageID);
    List<MessageVo> findByUserID(String userID);
    List<MessageVo> findAll(Pageable pageable);
}

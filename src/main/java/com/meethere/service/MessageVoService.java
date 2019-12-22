package com.meethere.service;

import com.meethere.entity.Message;
import com.meethere.entity.vo.MessageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageVoService  {
    MessageVo returnMessageVoByMessageID(int messageID);
    List<MessageVo> returnVo(List<Message> messages);
}

package com.meethere.service.impl;

import com.meethere.dao.MessageDao;
import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.service.MessageService;
import com.meethere.service.exception.LoginException;
import com.mysql.cj.protocol.x.XMessage;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    private Page<Message> getMessages(HttpServletRequest request, Pageable pageable, int stateReject) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        Page<Message> page=messageDao.findAllByUserIDAndState(loginUser.getUserID(), stateReject,pageable);
        return page;
    }

    @Override
    public Message findById(int messageID) {
        return messageDao.getOne(messageID);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageDao.findAllByState(STATE_PASS,pageable);
    }

    @Override
    public Page<Message> findByUser(HttpServletRequest request, Pageable pageable) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        Page<Message> page=messageDao.findAllByUserID(loginUser.getUserID(),pageable);
        return page;
    }

    @Override
    public Page<Message> findUserPass(HttpServletRequest request, Pageable pageable) {
        return getMessages(request, pageable, STATE_PASS);
    }

    @Override
    public Page<Message> findUserUnPass(HttpServletRequest request, Pageable pageable) {
        return getMessages(request, pageable, STATE_REJECT);
    }

    @Override
    public List<Message> findByUser(HttpServletRequest request) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        List<Message> messages=messageDao.findAllByUserID(loginUser.getUserID());
        return messages;
    }


    @Override
    public int create(Message message) {
        return messageDao.save(message).getMessageID();
    }

    @Override
    public void delById(int messageID) {
        messageDao.deleteById(messageID);
    }

    @Override
    public void update(Message message) {
        messageDao.save(message);
    }

    @Override
    public void confirmMessage(int messageID) {
        Message message=messageDao.findByMessageID(messageID);
        if(message==null)
            throw new RuntimeException("留言不存在");
        messageDao.updateState(STATE_PASS,message.getMessageID());
    }

    @Override
    public void rejectMessage(int messageID) {
        Message message=messageDao.findByMessageID(messageID);
        if(message==null)
            throw new RuntimeException("留言不存在");
        messageDao.updateState(STATE_REJECT,message.getMessageID());
    }

    @Override
    public Page<Message> findWaitState(Pageable pageable) {
        return messageDao.findAllByState(STATE_NO_AUDIT,pageable);
    }

    @Override
    public Page<Message> findPassState(Pageable pageable) {
        return messageDao.findAllByState(STATE_PASS,pageable);
    }

    @Override
    public Page<Message> findRejectState(Pageable pageable) {
        return messageDao.findAllByState(STATE_REJECT,pageable);

    }


}

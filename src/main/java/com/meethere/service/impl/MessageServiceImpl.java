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

    @Override
    public Message findById(int messageID) {
        return messageDao.getOne(messageID);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageDao.findAll(pageable);
    }

    @Override
    public Page<Message> findByUser(HttpServletRequest request, Pageable pageable) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        Message message=new Message();
        message.setUserID(loginUser.getUserID());

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("vendorId",ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Message> example = Example.of(message,exampleMatcher);
        Page<Message> page = messageDao.findAll(example,pageable);
        return page;
    }

    @Override
    public List<Message> findByUser(HttpServletRequest request) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        List<Message> messages=messageDao.findAllByUserID(((User) user).getUserID());
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
    public List<Message> findWaitState(int state) {
        return messageDao.findState(STATE_NO_AUDIT);
    }

    @Override
    public List<Message> findPassState(int state) {
        return messageDao.findState(STATE_PASS);
    }
}

package com.meethere.service.impl;

import com.meethere.dao.MessageDao;
import com.meethere.entity.Message;
import com.meethere.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



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
    public List<Message> findByUser(String userID) {
        return messageDao.findAllByUserID(userID);
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

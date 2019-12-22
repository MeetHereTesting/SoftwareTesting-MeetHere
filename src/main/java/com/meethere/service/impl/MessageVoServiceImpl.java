package com.meethere.service.impl;

import com.meethere.dao.MessageDao;
import com.meethere.dao.UserDao;
import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.entity.vo.MessageVo;
import com.meethere.service.MessageVoService;
import com.meethere.service.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageVoServiceImpl implements MessageVoService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Override
    public MessageVo findByMessageID(int messageID) {
        Message message=messageDao.findByMessageID(messageID);
        User user=userDao.findByUserID(message.getUserID());
        MessageVo messageVo=new MessageVo(message.getMessageID(),user.getUserID(),message.getContent(),message.getTime(),user.getUserName(),user.getPicture());

        return messageVo;
    }

    @Override
    public List<MessageVo> findByUserID(String userID) {
        User user=userDao.findByUserID(userID);
        List<Message> list=messageDao.findAllByUserID(userID);
        List<MessageVo> list1= new ArrayList<>();
        for(int i=0;i<list.size();i++){
            list1.add(findByMessageID(list.get(i).getMessageID()));
        }
        return list1;
    }

    @Override
    public List<MessageVo> findAll(Pageable pageable) {
        List<Message> list=messageDao.findAll(pageable).getContent();
        for(int i=0;i<list.size();i++)
            System.out.println(list.get(i).getMessageID());

        ArrayList<MessageVo> list1= new ArrayList<>();
        User user;
        for(int i=0;i<list.size();i++){
            MessageVo messageVo=new MessageVo();
            user=userDao.findByUserID(list.get(i).getUserID());

            messageVo.setMessageID(list.get(i).getMessageID());
            messageVo.setUserID(user.getUserID());
            messageVo.setContent(list.get(i).getContent());
            messageVo.setTime(list.get(i).getTime());
            messageVo.setUserName(user.getUserName());
            messageVo.setPicture(user.getPicture());

            list1.add(messageVo);
        }
        return list1;

    }

    @Override
    public List<MessageVo> findUserMessage(HttpServletRequest request) {
        Object user=request.getSession().getAttribute("user");
        if(user==null)
            throw new LoginException("请登录！");
        User loginUser=(User)user;
        List<MessageVo> messageVos=findByUserID(loginUser.getUserID());

        return messageVos;
    }

    @Override
    public List<MessageVo> returnVo(List<Message> messages) {
        List<MessageVo> list=new ArrayList<>();
        for(int i=0;i<messages.size();i++){
            list.add(findByMessageID(messages.get(i).getMessageID()));
        }
        return list;
    }
}

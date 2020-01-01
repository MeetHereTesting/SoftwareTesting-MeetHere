package com.meethere.service.impl;

import com.meethere.MeetHereApplication;
import com.meethere.dao.MessageDao;
import com.meethere.dao.UserDao;
import com.meethere.entity.Message;
import com.meethere.entity.User;
import com.meethere.entity.vo.MessageVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
class MessageVoServiceImplTest {
    @Mock
    private MessageDao messageDao;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private MessageVoServiceImpl messageVoService;

    @Test
    void return_messageVo_by_messageID() {
        int id=1;
        LocalDateTime ldt=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt,1);

        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(messageDao.findByMessageID(id)).thenReturn(message);
        when(userDao.findByUserID(userID)).thenReturn(user);
        MessageVo res=messageVoService.returnMessageVoByMessageID(id);
        assertAll(()->assertEquals(id,res.getMessageID()),()->assertEquals(userID,res.getUserID()),()->assertEquals("this is a leave message",res.getContent()),
                ()->assertEquals(ldt,res.getTime()),()->assertEquals(user_name,res.getUserName()),()->assertEquals(picture,res.getPicture()),
                ()->assertEquals(1,res.getState()));

        verify(messageDao).findByMessageID(id);
        verify(userDao).findByUserID(userID);
    }

    @Test
    void return_messageVo_list_by_message_list() {
        int id=1;
        LocalDateTime ldt=LocalDateTime.now().minusDays(1);
        Message message=new Message(id,"user","this is a leave message", ldt,1);
        List<Message> messages=new ArrayList<>();
        messages.add(message);

        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(messageDao.findByMessageID(id)).thenReturn(message);
        when(userDao.findByUserID(userID)).thenReturn(user);
        List<MessageVo> res=messageVoService.returnVo(messages);
        assertEquals(1,res.size());
        verify(messageDao).findByMessageID(id);
        verify(userDao).findByUserID(userID);
    }
}
package com.meethere.IntegrationTest.Service;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Message;
import com.meethere.service.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sun.rmi.runtime.Log;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@Transactional
public class MessageServiceTest {
    private static Logger LOG = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageService messageService;
    @BeforeEach
    @AfterEach
    public void print_all_message(){
        Pageable pageable= PageRequest.of(0,100, Sort.by("time").descending());
        List<Message> messages=messageService.findPassState(pageable).getContent();
        List<Message> messages1=messageService.findWaitState(pageable).getContent();
        LOG.info("{}",messages);
        LOG.info("{}",messages1);
    }


    @Test
    public void find_message_by_messageID(){
        int id=2;
        Message message=messageService.findById(id);
        assertEquals(2,message.getMessageID());
    }

    @Test
    public void find_message_by_userID() {
        Pageable pageable= PageRequest.of(0,100, Sort.by("time").descending());
        List<Message> messages=messageService.findByUser("test",pageable).getContent();
        for(Message i:messages){
            assertEquals("test",i.getUserID());
        }
    }

    @Test
    public void create_new_message() {
        LocalDateTime ldt=LocalDateTime.now().minusDays(1);
        Message message=new Message();
        message.setContent("this is a new message");
        message.setState(1);
        message.setTime(ldt);
        message.setUserID("user");
        messageService.create(message);
    }

    @Test
    @Transactional
    public void del_message_by_messageID() {
        messageService.delById(3);

        messageService.delById(2);

    }

    @Test
    public void update_message() {
        LocalDateTime ldt=LocalDateTime.now().minusDays(1);
        Message message=new Message();
        message.setMessageID(1);
        message.setContent("this is a new message");
        message.setState(1);
        message.setTime(ldt);
        message.setUserID("user");
        messageService.update(message);

    }

    @Test
    public void confirm_message_success() {
        int id=2;
        messageService.confirmMessage(id);
        messageService.findById(id);
    }

    @Test
    public void confirm_message_fail(){
        int id=1;
        assertThrows(RuntimeException.class,
                ()->messageService.confirmMessage(id),
                "留言不存在"    );

    }

    @Test
    public void reject_message_success() {
        int id=2;
        messageService.rejectMessage(id);

    }

    @Test
    public void reject_message_fail(){
        int id=1;

        assertThrows(RuntimeException.class,
                ()->messageService.rejectMessage(id),
                "留言不存在"    );

    }

    @Test
    public void find_wait_state_message() {
        int wait_state=1;
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());

        List<Message> messages= messageService.findWaitState(message_pageable).getContent();
        for(Message i:messages){
            assertEquals(wait_state,i.getState());
        }
    }

    @Test
    public void find_pass_state_message() {
        int pass_state=2;
        Pageable message_pageable= PageRequest.of(0,10, Sort.by("time").descending());

        List<Message> messages= messageService.findPassState(message_pageable).getContent();
        for(Message i:messages){
            assertEquals(pass_state,i.getState());
        }

    }
}

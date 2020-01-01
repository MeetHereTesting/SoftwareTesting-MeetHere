package com.meethere.IntegrationTest.Controller.User;

import com.meethere.MeetHereApplication;
import com.meethere.entity.Message;
import com.meethere.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserMessageApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void fail_return_message_list_html_when_user_not_login() throws Exception {
        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/message_list")),"请登录！");
    }

    @Test
    public void success_return_message_list_html_when_user_login() throws Exception {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);


        ResultActions perform=mockMvc.perform(get("/message_list").sessionAttr("user",user));
        ModelAndView mv=perform.andReturn().getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");
        assertModelAttributeAvailable(mv,"user_total");
    }

    @Test
    public void return_pass_message_list()throws Exception {
        ResultActions perform=mockMvc.perform(get("/message/getMessageList"));
        perform.andExpect(status().isOk());

    }

    @Test
    public void success_return_user_message_list_when_user_login() throws Exception{
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(1,userID,user_name,password,email,phone,isadmin,picture);

        ResultActions perform=mockMvc.perform(get("/message/findUserList").sessionAttr("user",user));
        perform.andExpect(status().isOk());

    }

    @Test
    public void fail_return_user_message_list_when_user_not_login() throws Exception{
        assertThrows(NestedServletException.class,()->mockMvc.perform(get("/message/findUserList")),"请登录！");
    }

    @Test
    public void user_add_new_message()throws Exception {
        ResultActions perform=mockMvc.perform(post("/sendMessage").param("userID","test").param("content","this is content"));
        perform.andExpect(redirectedUrl("/message_list"));
    }


    @Test
    public void user_modify_message()throws Exception {
        ResultActions perform=mockMvc.perform(post("/modifyMessage.do").param("messageID","2").param("userID","user").param("content","this is content"));
        perform.andExpect(content().string("true"));
    }

    @Test
    public void user_del_message()throws Exception {
        ResultActions perform=mockMvc.perform(post("/delMessage.do").param("messageID","2"));
        perform.andExpect(content().string("true"));


    }
}

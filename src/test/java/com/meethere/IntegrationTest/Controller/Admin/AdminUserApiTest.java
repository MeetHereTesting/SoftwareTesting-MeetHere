package com.meethere.IntegrationTest.Controller.Admin;

import com.meethere.MeetHereApplication;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminUserApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void return_user_manage_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/user_manage"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"total");

    }

    @Test
    public void return_user_add_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/user_add"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void return_user_list() throws Exception {
        ResultActions perform=mockMvc.perform(get("/userList.do").param("page","1"));
        perform.andExpect(status().isOk());

    }

    @Test
    public void return_user_edit_html() throws Exception {
        ResultActions perform=mockMvc.perform(get("/user_edit").param("id","1"));
        MvcResult mvcResult=perform.andReturn();
        ModelAndView mv=mvcResult.getModelAndView();
        perform.andExpect(status().isOk());
        assertModelAttributeAvailable(mv,"user");
    }

    @Test
    public void admin_modify_user() throws Exception {

        ResultActions perform=mockMvc.perform(post("/modifyUser.do").param("userID","test").param("name","userName").param("password","password")
                .param("email","email").param("phone","phone"));
        perform.andExpect(redirectedUrl("user_manage"));

    }

    @Test
    public void admin_add_user() throws Exception {
        ResultActions perform=mockMvc.perform(post("/addUser.do").param("userID","user").param("name","userName").param("password","password")
                .param("email","email").param("phone","phone"));
        perform.andExpect(redirectedUrl("user_manage"));
    }


    @Test
    public void return_already_exist_same_userID() throws Exception {
        ResultActions perform=mockMvc.perform(post("/checkUserID.do").param("userID","test"));
        perform.andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    public void return_not_exist_same_userID() throws Exception {
        ResultActions perform=mockMvc.perform(post("/checkUserID.do").param("userID","user"));
        perform.andExpect(status().isOk()).andExpect(content().string("true"));

    }

    @Test
    public void admin_del_user() throws Exception {
        ResultActions perform=mockMvc.perform(post("/delUser.do").param("id","1"));
        perform.andExpect(status().isOk());
    }
}

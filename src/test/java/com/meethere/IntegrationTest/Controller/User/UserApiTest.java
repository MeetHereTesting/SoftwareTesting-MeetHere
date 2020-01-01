package com.meethere.IntegrationTest.Controller.User;

import com.meethere.MeetHereApplication;
import com.meethere.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void return_sign_up_html() throws Exception {
        mockMvc.perform(get("/signup")).andExpect(status().isOk());
    }

    @Test
    public void return_login_html() throws Exception{
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    public void do_not_find_user_in_sql() throws Exception{
        ResultActions perform=mockMvc.perform(post("/loginCheck.do").param("userID","user").param("password","password"));
        perform.andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    public void find_user_in_sql() throws Exception{
        ResultActions perform=mockMvc.perform(post("/loginCheck.do").param("userID","test").param("password","test"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void find_admin_in_sql() throws Exception{
        ResultActions perform=mockMvc.perform(post("/loginCheck.do").param("userID","admin").param("password","admin"));
        perform.andExpect(status().isOk());
    }

    @Test
    public void register_a_new_user()throws Exception {
        ResultActions perform=mockMvc.perform(post("/register.do").param("userID","user").param("userName","name").param("password","password")
                .param("email","email").param("phone","phone"));
        perform.andExpect(redirectedUrl("login"));
    }

    @Test
    public void user_logout()throws Exception {
        ResultActions perform=mockMvc.perform(get("/logout.do"));
        perform.andExpect(redirectedUrl("/index"));
    }

    @Test
    public void user_update_info_when_passwordNews_is_null_and_picture_is_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", (String) null)
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));

    }

    @Test
    public void user_update_info_when_passwordNews_is_null_and_picture_is_not_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", (String) null)
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));

    }

    @Test
    public void user_update_info_when_passwordNews_is___and_picture_is_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", "")
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));

    }
    @Test
    public void user_update_info_when_passwordNews_is___and_picture_is_not_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", "")
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));
    }

    @Test
    public void user_update_info_when_passwordNews_is_not_null__and_picture_is_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","",
                "picture", "".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", "newPassword")
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));


    }

    @Test
    public void user_update_info_when_passwordNews_is_not_null__and_picture_is_not_null()throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("picture","1.bmp",
                "picture", "1.bmp".getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/updateUser.do").file(mockMultipartFile).param("userID","test").param("userName","name")
                        .param("passwordNew", "newPassword")
                        .param("email","email").param("phone","phone");
        ResultActions perform=mockMvc.perform(builder);
        perform.andExpect(redirectedUrl("user_info"));
    }


    @Test
    public void check_password_true()throws Exception {
        ResultActions perform=mockMvc.perform(get("/checkPassword.do").param("userID","test").param("password","test"));
        perform.andExpect(status().isOk()).andExpect(content().string("true"));
    }


    @Test
    public void check_password_false()throws Exception {
        ResultActions perform=mockMvc.perform(get("/checkPassword.do").param("userID","test").param("password","password"));
        perform.andExpect(status().isOk()).andExpect(content().string("false"));
    }

    @Test
    public void return_user_info_html() throws Exception{
        mockMvc.perform(get("/user_info").sessionAttr("user",new User())).andExpect(status().isOk());
    }
}

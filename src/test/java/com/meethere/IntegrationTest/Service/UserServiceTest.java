package com.meethere.IntegrationTest.Service;

import com.meethere.MeetHereApplication;
import com.meethere.entity.User;
import com.meethere.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void return_user_ByUserID() {
        String userID="user";
        User res=userService.findByUserID(userID);
        assertNull(res);
    }

    @Test
    void return_user_by_id() {
        int id=1;

        User res=userService.findById(id);
        assertEquals(id,res.getId());
        assertEquals("test",res.getUserID());
    }

    @Test
    void return_user_list_paged() {
        Pageable pageable= PageRequest.of(0,10);
        userService.findByUserID(pageable);
    }

    @Test
    void check_userID_and_password_matched() {
        String userID="test";
        String password="test";

        User res=userService.checkLogin(userID,password);
        assertEquals(userID,res.getUserID());
        assertEquals(password,res.getPassword());
    }

    @Test
    void register_a_new_user() {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        int res=userService.create(user);
        assertTrue(res>0);
    }

    @Test
    void del_user_by_id() {
        userService.delByID(1);

        userService.delByID(6);
    }

    @Test
    void update_user_info() {
        int id=1;
        String userID="test";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        userService.updateUser(user);
    }

    @Test
    void return_number_of_same_userID() {
        String userID="test";
        int res1=userService.countUserID(userID);
        assertEquals(1,res1);

    }
}

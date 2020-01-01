package com.meethere.service.impl;

import com.meethere.MeetHereApplication;
import com.meethere.dao.UserDao;
import com.meethere.entity.User;
import com.meethere.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeetHereApplication.class)
class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void return_user_ByUserID() {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(userDao.findByUserID(userID)).thenReturn(user);
        User res=userService.findByUserID(userID);

        assertAll("test find by userID",()->assertEquals(id,res.getId()),
                ()->assertEquals(userID,res.getUserID()),
                ()->assertEquals(password,res.getPassword()),
                ()->assertEquals(email,res.getEmail()),
                ()->assertEquals(phone,res.getPhone()),
                ()->assertEquals(isadmin,res.getIsadmin()),
                ()->assertEquals(user_name,res.getUserName()),
                ()->assertEquals(picture,res.getPicture()));

        verify(userDao).findByUserID(userID);
    }

    @Test
    void return_user_by_id() {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(userDao.findById(id)).thenReturn(user);
        User res=userService.findById(id);

        assertAll("test find by userID",()->assertEquals(id,res.getId()),
                ()->assertEquals(userID,res.getUserID()),
                ()->assertEquals(password,res.getPassword()),
                ()->assertEquals(email,res.getEmail()),
                ()->assertEquals(phone,res.getPhone()),
                ()->assertEquals(isadmin,res.getIsadmin()),
                ()->assertEquals(user_name,res.getUserName()),
                ()->assertEquals(picture,res.getPicture()));
        verify(userDao).findById(id);
    }

    @Test
    void return_user_list_paged() {
        Pageable pageable= PageRequest.of(0,10);
        when(userDao.findAllByIsadmin(0,pageable)).thenReturn(null);
        userService.findByUserID(pageable);
        verify(userDao).findAllByIsadmin(0,pageable);
    }

    @Test
    void check_userID_and_password_matched() {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(userDao.findByUserIDAndPassword(userID,password)).thenReturn(user);
        User res=userService.checkLogin(userID,password);

        assertAll("test find by userID",()->assertEquals(id,res.getId()),
                ()->assertEquals(userID,res.getUserID()),
                ()->assertEquals(password,res.getPassword()),
                ()->assertEquals(email,res.getEmail()),
                ()->assertEquals(phone,res.getPhone()),
                ()->assertEquals(isadmin,res.getIsadmin()),
                ()->assertEquals(user_name,res.getUserName()),
                ()->assertEquals(picture,res.getPicture()));
        verify(userDao).findByUserIDAndPassword(userID,password);
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

        when(userDao.save(user)).thenReturn(null);
        userService.create(user);
        verify(userDao).save(user);
        verify(userDao).findAll();

    }

    @Test
    void del_user_by_id() {
        userService.delByID(1);
        verify(userDao).deleteById(1);

        userService.delByID(2);
        verify(userDao).deleteById(2);

        verify(userDao,times(2)).deleteById(anyInt());

    }

    @Test
    void update_user_info() {
        int id=1;
        String userID="user";
        String password="password";
        String email="222@qq.com";
        String phone="12345678901";
        int isadmin=0;
        String user_name="nickname";
        String picture="picture";
        User user=new User(id,userID,user_name,password,email,phone,isadmin,picture);

        when(userDao.save(user)).thenReturn(null);
        userService.updateUser(user);
        verify(userDao).save(user);
    }

    @Test
    void return_number_of_same_userID() {
        String userID="user";
        when(userDao.countByUserID(userID)).thenReturn(1).thenReturn(2);
        int res1=userService.countUserID(userID);
        assertEquals(1,res1);
        int res2=userService.countUserID(userID);
        assertEquals(2,res2);
        verify(userDao,times(2)).countByUserID(userID);

    }
}
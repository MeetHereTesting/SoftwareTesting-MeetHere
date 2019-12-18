package com.meethere.service.impl;

import com.meethere.dao.UserDao;
import com.meethere.entity.User;
import com.meethere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserID(String userID) {
        return userDao.findByUserID(userID);
    }

    @Override
    public Page<User> findByUserID(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public User checkLogin(String userID, String password) {
        return userDao.findByUserIDAndPassword(userID,password);
    }

    @Override
    public int create(User user) {
        userDao.save(user);
        return userDao.findAll().size();
    }

    @Override
    public void delByUserID(String userID) {
        userDao.deleteByUserID(userID);
    }
}

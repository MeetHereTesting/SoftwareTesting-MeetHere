package com.meethere.dao;

import com.meethere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
    User findByUserIDAndPassword(String userID, String password);
    User findByUserID(String userID);
    void deleteByUserID(String userID);
}

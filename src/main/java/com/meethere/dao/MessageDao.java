package com.meethere.dao;

import com.meethere.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message,Integer> {
    List<Message> findAllByUserID(String userID);
}

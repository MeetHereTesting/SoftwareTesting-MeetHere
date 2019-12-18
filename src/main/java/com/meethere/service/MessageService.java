package com.meethere.service;

import com.meethere.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {

    Message findById(int messageID);

    /**
     * 分页查询所有留言
     *
     * @param pageable
     * @return
     */
    Page<Message> findAll(Pageable pageable);

    /**
     *user查看自己的留言
     *
     * @param userID
     * @return
     */
    List<Message> findByUser(String userID);


    /**
     * 添加留言
     *
     * @param message
     * @return
     */
    int create(Message message);

    /**
     * 删除留言
     *
     * @param messageID
     */
    void delById(int messageID);
}

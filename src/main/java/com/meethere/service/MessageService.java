package com.meethere.service;

import com.meethere.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageService {

    int STATE_NO_AUDIT=1;
    int STATE_PASS=2;
    int STATE_REJECT=3;

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
     * @return
     */
     List<Message> findByUser(HttpServletRequest request);

    /**
     * 分页返回用户留言
     * @param request
     * @param pageable
     * @return
     */
     Page<Message> findByUser(HttpServletRequest request,Pageable pageable);

     Page<Message> findUserPass(HttpServletRequest request,Pageable pageable);

     Page<Message> findUserUnPass(HttpServletRequest request,Pageable pageable);

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

    void update(Message message);

    void confirmMessage(int messageID);

    void rejectMessage(int messageID);

    Page<Message> findWaitState(Pageable pageable);

    Page<Message> findPassState(Pageable pageable);

    Page<Message> findRejectState(Pageable pageable);
}

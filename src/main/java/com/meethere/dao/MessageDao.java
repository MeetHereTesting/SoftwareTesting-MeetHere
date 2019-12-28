package com.meethere.dao;

import com.meethere.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageDao extends JpaRepository<Message,Integer> {
    Message findByMessageID(int messageID);

    Page<Message> findAllByUserID(String userID,Pageable pageable);
    
    Page<Message> findAllByState(int state,Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="update Message o set o.state=?1 where o.messageID=?2",nativeQuery =true)
    void updateState(int state, int messageID);

}

package com.meethere.dao;

import com.meethere.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageDao extends JpaRepository<Message,Integer> {
    List<Message> findAllByUserID(String userID);
    Message findByMessageID(int messageID);

    @Transactional
    @Modifying
    @Query(value="update Message o set o.state=?1 where o.messageID=?2",nativeQuery =true)
    void updateState(int state, int messageID);

    @Query(value="select * from Message m where m.state=?1",nativeQuery = true)
    List<Message> findState(int state);
}

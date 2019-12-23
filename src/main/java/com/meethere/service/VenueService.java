package com.meethere.service;

import com.meethere.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VenueService {
    /**
     * 根据体育馆id查看
     *
     * @param id
     * @return
     */
    Venue findByVenueID(int id);

    Venue findByVenueName(String venueName);
    /**
     * 分页查看所有场馆
     * @param pageable
     * @return
     */
    Page<Venue> findAll(Pageable pageable);

    List<Venue> findAll();
    /**
     * 创建新的场馆
     *
     * @param venue
     * @return
     */
    int create(Venue venue);

    /**
     * 更新场馆信息
     *
     * @param venue
     */
    void update(Venue venue);

    /**
     * 删除场馆
     *
     * @param id
     */
    void delById(int id);

}

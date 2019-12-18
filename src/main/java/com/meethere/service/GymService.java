package com.meethere.service;

import com.meethere.entity.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GymService {
    /**
     * 根据体育馆id查看
     *
     * @param id
     * @return
     */
    Gym findByGymID(int id);

    /**
     * 分页查看所有场馆
     * @param pageable
     * @return
     */
    Page<Gym> findAll(Pageable pageable);

    /**
     * 创建新的场馆
     *
     * @param gym
     * @return
     */
    int create(Gym gym);

    /**
     * 更新场馆信息
     *
     * @param gym
     */
    void update(Gym gym);

    /**
     * 删除场馆
     *
     * @param id
     */
    void delById(int id);
}

package com.meethere.service;

import com.meethere.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    /**
     * 根据用户id查找用户
     *
     * @param userID
     * @return
     */
    User findByUserID(String userID);
    User findById(int id);
    /**
     * 管理员分页查看用户
     *
     * @param pageable
     * @return
     */
    Page<User> findByUserID(Pageable pageable);

    /**
     * 检查登录
     *
     * @param userID
     * @param password
     * @return
     */
    User checkLogin(String userID, String password);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 根据id删除用户
     *
     * @param id
     */
    void delByID(int id);

    void updateUser(User user);

    int countUserID(String userID);
}

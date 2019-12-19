package com.meethere.dao;

import com.meethere.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymDao extends JpaRepository<Gym, Integer> {
    Gym findByGymID(int gymID);
}

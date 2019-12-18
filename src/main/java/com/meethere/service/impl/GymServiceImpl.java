package com.meethere.service.impl;

import com.meethere.dao.GymDao;
import com.meethere.entity.Gym;
import com.meethere.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GymServiceImpl implements GymService {
    @Autowired
    private GymDao gymDao;

    @Override
    public Gym findByGymID(int id) {
        return gymDao.getOne(id);
    }

    @Override
    public Page<Gym> findAll(Pageable pageable) {
        return gymDao.findAll(pageable);
    }

    @Override
    public int create(Gym gym) {
        return gymDao.save(gym).getGymID();
    }

    @Override
    public void update(Gym gym) {
        gymDao.save(gym);
    }

    @Override
    public void delById(int id) {
        gymDao.deleteById(id);
    }
}

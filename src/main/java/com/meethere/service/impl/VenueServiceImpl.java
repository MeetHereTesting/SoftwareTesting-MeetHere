package com.meethere.service.impl;

import com.meethere.dao.VenueDao;
import com.meethere.entity.Venue;
import com.meethere.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    private VenueDao venueDao;

    @Override
    public Venue findByVenueID(int id) {
        return venueDao.getOne(id);
    }

    @Override
    public Page<Venue> findAll(Pageable pageable) {
        return venueDao.findAll(pageable);
    }

    @Override
    public int create(Venue venue) {
        return venueDao.save(venue).getVenueID();
    }

    @Override
    public void update(Venue venue) {
        venueDao.save(venue);
    }

    @Override
    public void delById(int id) {
        venueDao.deleteById(id);
    }
}

package com.meethere.dao;

import com.meethere.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VenueDao extends JpaRepository<Venue, Integer> {
    Venue findByVenueID(int venueID);

    @Query(value = "select * from Venue",nativeQuery = true)
    List<Venue> findAll();
}

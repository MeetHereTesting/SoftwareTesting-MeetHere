package com.meethere.dao;

import com.meethere.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueDao extends JpaRepository<Venue, Integer> {
    Venue findByVenueID(int venueID);
}

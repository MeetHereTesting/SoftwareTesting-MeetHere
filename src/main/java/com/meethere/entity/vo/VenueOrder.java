package com.meethere.entity.vo;

import com.meethere.entity.Order;
import com.meethere.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueOrder {
    Venue venue;
    List<Order> orders;
}

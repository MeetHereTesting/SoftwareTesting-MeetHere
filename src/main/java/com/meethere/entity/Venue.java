package com.meethere.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int venueID;

    @Column(name="venue_name")
    private String venueName;

    private String description;

    private int price;

    private String picture;

    private String address;

    private String open_time;

    private String close_time;
}

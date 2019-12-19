package com.meethere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Gym{
    @Id
    @GeneratedValue
    private int gymID;

    @Column(name="gym_name")
    private String gymName;

    private String description;

    private int price;

    private String picture;
}

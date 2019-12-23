package com.meethere.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userID;

    @Column(name="user_name")
    private String userName;

    private String password;

    private String email;

    private String phone;

    private int isadmin;

    private String picture;
}

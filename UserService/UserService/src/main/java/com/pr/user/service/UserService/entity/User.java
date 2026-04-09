package com.pr.user.service.UserService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name="ID")
    private String userId;
    @Column(name="NAME",nullable = false,length = 15)
    private String name;
    @Column(name="EMAIL",nullable = false)
    private String email;
    @Column(name="ABOUT",nullable = false)
    private String about;

    //Transient matlab ye database me store nhi hoga
    @Transient
    private List<Rating> ratings=new ArrayList<>();
}

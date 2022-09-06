package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;

    private Date dateOfBirth;

    private String number;

    private String role ;

    private String username;

    private String password;

    private String address;

    private String MSSV;

//    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
//    private Set<Issue> issues;
}

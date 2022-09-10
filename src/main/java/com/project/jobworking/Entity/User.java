package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column
    private String name;

    @Column
    private Date dateOfBirth;

    @Column
    private String number;

    @Column
    private String role ;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private String identify;

    private boolean enabled = true;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Job> jobs;
}

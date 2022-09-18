package com.project.jobworking.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Project extends BaseEntity {

    @Column(name = "project_name")
    private String name;

    @Column
    private String description;

    @Column
    private Boolean isUsed ;

    @Column(name = "progress")
    private String progress;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Job> jobs;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Media> mediaList;
}

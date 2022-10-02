package com.project.jobworking.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Job> jobs;

}

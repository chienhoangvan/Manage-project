package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Job extends BaseEntity {
    @Column
    private String job_name;

    @Column
    private String description;

    @Column
    private String status;

    @Column
    private Date start_date;

    @Column
    private Date end_date;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "job", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments;
}

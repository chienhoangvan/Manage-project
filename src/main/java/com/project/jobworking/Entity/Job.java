package com.project.jobworking.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Job extends BaseEntity {
    @Column(name = "job_name")
    private String name;

    @Column
    private String description;

    @Column
    private String status;

    @Column(name = "startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "job", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments;

}

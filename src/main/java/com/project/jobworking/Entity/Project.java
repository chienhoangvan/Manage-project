package com.project.jobworking.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Project extends BaseEntity {

    @Column
    private String project_name;

    @Column
    private String description;

    @Column
    private String status;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Job> jobs;
}

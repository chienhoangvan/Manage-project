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

    @OneToMany(mappedBy = "project",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Set<User> users;

    @OneToMany(mappedBy = "project",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Set<Job> jobs;
}

package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment extends BaseEntity {
    @Column
    private String substance;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;
}

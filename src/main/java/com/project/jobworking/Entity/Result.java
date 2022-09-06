package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Result extends BaseEntity {
    @Column
    private String progress;

    @Column
    private String result;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Job job;
}

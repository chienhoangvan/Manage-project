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

    @Column(name = "result_name")
    private String resultName;

    @Column(name = "project_id")
    private Long projectId;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private User user;

}

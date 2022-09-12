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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

}

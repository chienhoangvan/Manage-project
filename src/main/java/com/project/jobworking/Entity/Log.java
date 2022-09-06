package com.project.jobworking.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Log extends BaseEntity {
    @Column
    private String substance;
}

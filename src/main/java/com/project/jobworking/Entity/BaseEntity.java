package com.project.jobworking.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createdDate;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @LastModifiedBy
    protected String modifiedBy;
}

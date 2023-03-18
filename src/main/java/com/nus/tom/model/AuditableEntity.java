package com.nus.tom.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Data
public class AuditableEntity {


    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Instant createdOn;
    @LastModifiedBy
    private String modifiedBy;
    @LastModifiedDate
    private Instant modifiedOn;

}

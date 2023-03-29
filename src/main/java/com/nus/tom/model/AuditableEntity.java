package com.nus.tom.model;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@MappedSuperclass
public class AuditableEntity {


    @Column(updatable = false,name = "createdBy")
    private String createdBy="System";
    @Column(updatable = false,name = "createdOn")
    private Instant createdOn;
    @Column(name = "modifiedBy")
    private String modifiedBy="System";
    @Column(name = "modifiedOn")
    private Instant modifiedOn;

    @PrePersist
    public void setCreatedOn() {
        if (this.createdOn == null) {
            this.createdOn = Instant.now();
        }
        if (this.modifiedOn == null) {
            this.modifiedOn = Instant.now();
        }
    }

    @PreUpdate
    public void setModifiedOn() {
        if (this.modifiedOn == null) {
            this.modifiedOn = Instant.now();
        }
    }

}

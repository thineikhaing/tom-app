package com.nus.tom.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "project")
public class Project extends AuditableEntity implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "startDate")
    private Instant startDate;
    @Column(name = "endDate")
    private Instant endDate;

    @OneToMany(mappedBy = "project")
    private Set<Employee> employees;

    @Column
    private int priority;

}

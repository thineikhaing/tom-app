package com.nus.tom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ProjectDTO implements Serializable {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private Set<EmployeeDTO> employees;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, String name, Instant startDate, Instant endDate, Set<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employees = employees;
    }

}

package com.nus.tom.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }
}

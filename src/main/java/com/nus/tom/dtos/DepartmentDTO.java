package com.nus.tom.dtos;

import java.util.Set;

//DepartmentResponse class that represents the response body for Department operations
public class DepartmentDTO {

    private String id;

    private String name;

    private EmployeeDTO departmentHead;

    private Set<EmployeeDTO> employees;

    private String details;

    public DepartmentDTO() {}

    public DepartmentDTO(String id, String name, EmployeeDTO departmentHead, Set<EmployeeDTO> employees, String details) {
        this.id = id;
        this.name = name;
        this.departmentHead = departmentHead;
        this.employees = employees;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeDTO getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(EmployeeDTO departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
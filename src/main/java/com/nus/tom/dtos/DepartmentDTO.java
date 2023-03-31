package com.nus.tom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
//DepartmentResponse class that represents the response body for Department operations
public class DepartmentDTO {

    private String id;

    private String name;

//    private EmployeeDTO departmentHead;

    private Set<EmployeeDTO> employees;

    private String details;

    public DepartmentDTO() {}

    public DepartmentDTO(String id, String name, Set<EmployeeDTO> employees, String details) {
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.details = details;
    }

}
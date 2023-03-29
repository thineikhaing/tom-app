package com.nus.tom.payload.response;

import com.nus.tom.model.Employee;
//DepartmentResponse class that represents the response body for Department operations
public class DepartmentResponse {

    private Long id;
    private String name;
    private Employee departmentHead;

    public DepartmentResponse(Long id, String name, Employee departmentHead) {
        this.id = id;
        this.name = name;
        this.departmentHead = departmentHead;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employee getDepartmentHead() {
        return departmentHead;
    }
}

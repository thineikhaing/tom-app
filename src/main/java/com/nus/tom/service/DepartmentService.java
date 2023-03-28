package com.nus.tom.service;

import com.nus.tom.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(String id);

    Department createDepartment(Department department);

    Department updateDepartment(String id, Department department);

    void deleteDepartment(String id);

}

package com.nus.tom.service;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(String id);

    Department createDepartment(Department department);

//    Department updateDepartment(String id, Department department);

    Department updateDepartment(String id, Department department);

    void deleteDepartment(String id);

    Department assignDepartmentHead(String departmentId, String employeeId);
}


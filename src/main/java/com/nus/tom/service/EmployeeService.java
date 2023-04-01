package com.nus.tom.service;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<ResponseValueObject> save(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(String id, Employee employee);
    void deleteEmployee(String id);

}

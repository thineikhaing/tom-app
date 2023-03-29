package com.nus.tom.service;

import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<ResponseValueObject> save(Employee employee);

    Employee addEmployee(Employee employee);
}

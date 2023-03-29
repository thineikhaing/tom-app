package com.nus.tom.controller;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.EmployeeService;

import com.nus.tom.util.ResourceNotFoundException;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(path = "/saveEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<Employee> addEmployeeToDepartment(@RequestBody Employee employee, @PathVariable Long departmentId) {
        Department department = departmentService.getDepartmentById(String.valueOf(departmentId));
        if (department == null) {
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        }

        employee.setDepartment(department);
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

}

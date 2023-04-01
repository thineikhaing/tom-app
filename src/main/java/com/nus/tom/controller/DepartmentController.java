package com.nus.tom.controller;

import com.nus.tom.dtos.DepartmentDTO;
import com.nus.tom.mappers.DepartmentMapper;
import com.nus.tom.model.Department;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.EmployeeService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private final EmployeeService employeeService;

    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDTO> departmentDTOs = departments.stream()
                .map(department -> departmentMapper.toDepartmentDTO(department))
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentDTOs);
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") String id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable("id") String id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
    }

    // POST mapping to assign department head
//    @PostMapping("/{departmentId}/assignHead")
//    public ResponseEntity<?> assignDepartmentHead(@PathVariable(value = "departmentId") String departmentId, @RequestBody Employee employee) {
//        Department department = departmentService.assignDepartmentHead(departmentId, employee);
//        return new ResponseEntity<>(department, HttpStatus.OK);
//    }


}

package com.nus.tom.controller;

import com.nus.tom.dtos.DepartmentDTO;
import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    //    private final DepartmentMapper departmentMapper;
    @Autowired
    private ModelMapper modelMapper;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDTO> departmentDTOs = departments.stream()
                .map(DepartmentDTO::fromDepartment)
                .collect(Collectors.toList());
        return departmentDTOs;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable String id) {

        Department department = departmentService.getDepartmentById(id);

        // convert entity to DTO
        DepartmentDTO departmentResponse = DepartmentDTO.fromDepartment(department);

        return ResponseEntity.ok().body(departmentResponse);
    }


    @PostMapping("")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {

        // convert DTO to entity
        Department departmentRequest = modelMapper.map(departmentDTO, Department.class);
        Department department = departmentService.createDepartment(departmentRequest);
        // convert entity to DTO
        DepartmentDTO departmentResponse = DepartmentDTO.fromDepartment(department);
        return new ResponseEntity<DepartmentDTO>(departmentResponse, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") String id, @RequestBody DepartmentDTO departmentDTO) {

        // convert DTO to Entity
        Department departmentRequest = DepartmentDTO.toDepartment(departmentDTO);

        Department department = departmentService.updateDepartment(id, departmentRequest);

        // entity to DTO
        DepartmentDTO departmentResponse = DepartmentDTO.fromDepartment(department);//modelMapper.map(department, DepartmentDTO.class);

        return ResponseEntity.ok().body(departmentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    //     POST mapping to assign department head
    @PostMapping("/assign-department-head")
//public ResponseEntity<String> assignDepartmentHead(@RequestParam String employeeId, @RequestParam String departmentId) {
    public ResponseEntity<DepartmentDTO> assignDepartmentHead(@RequestBody Map<String, String> body) {
        String departmentId = body.get("departmentId");
        String employeeId = body.get("employeeId");
        DepartmentDTO departmentDTO = new DepartmentDTO();

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentDTO);
        }

        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentDTO);
        }

        Employee employee = employeeOptional.get();
        Department department = departmentOptional.get();

        department.setDepartmentHead(employee);
        departmentRepository.save(department);


        return ResponseEntity.ok(DepartmentDTO.fromDepartment(department));
    }


}

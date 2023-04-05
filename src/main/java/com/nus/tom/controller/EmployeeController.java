package com.nus.tom.controller;

import com.nus.tom.dtos.*;
import com.nus.tom.model.*;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ModelMapper modelMapper;
    @PostMapping(path = "/saveEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    // Create
    @PostMapping("")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        // convert DTO to entity
        Employee empRequest = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = employeeService.createEmployee(empRequest);
        // convert entity to DTO
        EmployeeDTO empResponse = EmployeeDTO.fromEmployee(employee);
        return new ResponseEntity<EmployeeDTO>(empResponse, HttpStatus.CREATED);
    }

    // Read All
    //retrieve a list of Employee entities from the database and convert them to EmployeeDTO objects before returning them to the client
    @GetMapping("")
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs).getBody();
    }


    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {

        Employee employee = employeeService.getEmployeeById(id);
        // convert entity to DTO
        EmployeeDTO employeeResponse = EmployeeDTO.fromEmployee(employee);
        return ResponseEntity.ok().body(employeeResponse);

    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") String id,
                                                      @RequestBody EmployeeDTO employeeDTO) {

        // convert DTO to Entity
        Employee employeeRequest = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = employeeService.updateEmployee(id, employeeRequest);
        // entity to DTO
        EmployeeDTO employeeResponse = EmployeeDTO.fromEmployee(employee);
        return ResponseEntity.ok().body(employeeResponse);

    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/department/{id}")
    public List<EmployeeDTO> getEmployeesByDepartmentID(@PathVariable String id) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentID(id);
        List<EmployeeDTO> employeeDTOs =employees.stream()
                .map(EmployeeDTO::fromEmployee).collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs).getBody();

    }


}

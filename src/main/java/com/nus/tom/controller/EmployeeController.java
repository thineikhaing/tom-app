package com.nus.tom.controller;

import com.nus.tom.dtos.*;
import com.nus.tom.mappers.EmployeeMapper;
import com.nus.tom.model.*;
import com.nus.tom.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final EmployeeMapper employeeMapper;

    @PostMapping(path = "/saveEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    // Create
    @PostMapping("")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // Read All
    //retrieve a list of Employee entities from the database and convert them to EmployeeDTO objects before returning them to the client
    @GetMapping("")

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream()
                .map(employeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }


    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        EmployeeDTO employeeDTO = employeeMapper.toEmployeeDTO(employee);
        return ResponseEntity.ok().body(employeeDTO);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") String id,
                                                      @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        EmployeeDTO updatedEmployeeDTO = employeeMapper.toEmployeeDTO(updatedEmployee);
        return ResponseEntity.ok().body(updatedEmployeeDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }




}

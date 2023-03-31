package com.nus.tom.service.impl;


import com.nus.tom.model.*;
import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.RoleRepository;
import com.nus.tom.repository.UserRepository;
import com.nus.tom.service.EmployeeService;
//import com.nus.tom.util.ResourceNotFoundException;
import com.nus.tom.util.ResourceNotFoundException;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
    private final ResponseHelper responseHelper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<ResponseValueObject> save(Employee employee) {
        try {
            log.info("Save employee {}", employee.getFullName());
            employeeRepository.save(employee);
            return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, employee.getId());
        } catch (Exception ex) {
            log.error("Exception in saving employee {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, employee.getId());
        }

    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", employee.getDepartment().getId()));
        employee.setDepartment(department);


        User user = new User();
        user.setUsername(employee.getFullName().replaceAll(" ", "_").toLowerCase());
        user.setEmail(employee.getEmail());
        user.setPassword(encoder.encode("password"));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        User newuser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found","id", user.getId()));

        employee.setUser(newuser);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", employee.getDepartment().getId()));
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setContactNumber(employee.getContactNumber());
        existingEmployee.setLeaveBalance(employee.getLeaveBalance());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setEmployment_startDate(employee.getEmployment_startDate());
        existingEmployee.setEmployment_endDate(employee.getEmployment_endDate());
        existingEmployee.setDepartment(department);
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }


}

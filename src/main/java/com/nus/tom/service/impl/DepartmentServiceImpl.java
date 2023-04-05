package com.nus.tom.service.impl;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Department> getAllDepartments() {
        log.info("Get ALl Department {}",departmentRepository.findAll().size());
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            log.info("Get Department - {}",department.get().getName());
            return department.get();
        } else {
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }

    @Override
    public Department createDepartment(Department department) {
        Department newDepartment = departmentRepository.save(department);
        log.info("Created Department - {}",newDepartment.getName());
        return newDepartment;
    }

    @Override
    public Department updateDepartment(String id, Department department) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("department", "id", id));

        existingDepartment.setName(department.getName());
        existingDepartment.setDetails(department.getDetails());
        existingDepartment.setDepartmentHead(department.getDepartmentHead());

        if (department.getDepartmentHead() != null) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(department.getDepartmentHead().getId());
            if (optionalEmployee.isPresent()) {
                Employee departmentHead = optionalEmployee.get();
                existingDepartment.setDepartmentHead(departmentHead);
                log.info("DepartmentHead - {}",departmentHead.getFullName());
            } else {
                throw new ResourceNotFoundException("Employee", "id", department.getDepartmentHead().getId());
            }
        }

        return departmentRepository.save(existingDepartment);

    }

    @Override
    public void deleteDepartment(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            log.info("Department to delete - {}",department.get().getName());
            departmentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }


    @Override
    public Department assignDepartmentHead(String departmentId, String employeeId){

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        Employee assignEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        department.setDepartmentHead(assignEmployee);
        departmentRepository.save(department);
        log.info("Department Head assigned - {}",department.getDepartmentHead().getFullName());
        return department;
    }


}

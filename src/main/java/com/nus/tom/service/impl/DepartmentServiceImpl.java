package com.nus.tom.service.impl;

import com.nus.tom.model.Department;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        } else {
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(String id, Department department) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            department.setId(id);
            return departmentRepository.save(department);
        } else {
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }

    @Override
    public void deleteDepartment(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }
}

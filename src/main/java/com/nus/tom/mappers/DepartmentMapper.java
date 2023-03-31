package com.nus.tom.mappers;

import com.nus.tom.dtos.DepartmentDTO;
import com.nus.tom.dtos.EmployeeDTO;
import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class DepartmentMapper {

    private final ModelMapper modelMapper;

    public DepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public DepartmentDTO toDepartmentDTO(Department department) {
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        Set<EmployeeDTO> employeeDTOs = department.getEmployees()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toSet());
        departmentDTO.setEmployees(employeeDTOs);
        return departmentDTO;
    }

    public Department toDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        Set<Employee> employees = departmentDTO.getEmployees()
                .stream()
                .map(employeeDTO -> modelMapper.map(employeeDTO, Employee.class))
                .collect(Collectors.toSet());
        department.setEmployees(employees);
        return department;
    }

}


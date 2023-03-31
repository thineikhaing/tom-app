package com.nus.tom.mappers;

import com.nus.tom.dtos.*;
import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.util.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper{

    private final ModelMapper modelMapper;

    @Autowired
    private DepartmentRepository departmentRepository;
    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        // Configure ModelMapper mappings here if needed
    }

    public EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        if (employee.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(employee.getDepartment().getId());
            departmentDTO.setName(employee.getDepartment().getName());
            employeeDTO.setDepartment(departmentDTO);
        }

        if (employee.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(employee.getUser().getId());
            userDTO.setUsername(employee.getUser().getUsername());
            userDTO.setEmail(employee.getUser().getEmail());

            Set<String> roles = employee.getUser().getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toSet());
            userDTO.setRoles(roles);
            employeeDTO.setUser(userDTO);
        }

        if (employee.getProject() != null) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(employee.getProject().getId());
            projectDTO.setName(employee.getProject().getName());
            employeeDTO.setProject(projectDTO);
        }

        Set<LeaveDTO> leaveDTOs = new HashSet<>();
        if (employee.getLeaves() != null) {
            for (Leave leave : employee.getLeaves()) {
                LeaveDTO leaveDTO = new LeaveDTO(leave);
                leaveDTO.setId(leave.getId());
                leaveDTO.setName(leave.getName());
                leaveDTO.setStartDate(leave.getStartDate());
                leaveDTO.setEndDate(leave.getEndDate());
                leaveDTO.setStatus(leave.getStatus());
                leaveDTO.setLeaveType(leave.getLeaveType());
                leaveDTOs.add(leaveDTO);
            }
        }
        employeeDTO.setLeaves(leaveDTOs);

        return employeeDTO;
    }

    public void updateEntity(EmployeeDTO employeeDTO, Employee employee) {
        modelMapper.map(employeeDTO, employee);
        // Add any additional mapping logic here if needed
    }

    public Employee toEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = new Employee();
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setId(employeeDTO.getId());
        employee.setFullName(employeeDTO.getFullName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setContactNumber(employeeDTO.getContactNumber());
        employee.setLeaveBalance(employeeDTO.getLeaveBalance());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setEmployment_startDate(employeeDTO.getEmployment_startDate());
        employee.setEmployment_endDate(employeeDTO.getEmployment_endDate());
        Department department = departmentRepository.findById(employeeDTO.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", employeeDTO.getDepartment().getId()));
        employee.setDepartment(department);
        return employee;
    }
}

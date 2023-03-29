package com.nus.tom.dtos;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        // Configure ModelMapper mappings here if needed
    }

    public EmployeeDTO toDto(Employee employee) {
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

//            Set<String> roles = employee.getUser().getRoles().stream()
//                    .map(role -> role.getName().toString())
//                    .collect(Collectors.toSet());
//
//            userDTO.setRoles(roles);
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

    public Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        // Add any additional mapping logic here if needed
        return employee;
    }

    public void updateEntity(EmployeeDTO employeeDTO, Employee employee) {
        modelMapper.map(employeeDTO, employee);
        // Add any additional mapping logic here if needed
    }

}

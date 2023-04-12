package com.nus.tom.dtos;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.model.enums.ERole;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private String id;
    private String departmentId;
    private String departmentName;
    private String departHeadId;
    private String departHeadFullName;
    private String username;
    private List<ERole> roles;
    private String fullName;
    private String address;
    private String contactNumber;
    private Date dateOfBirth;
    private Date employmentStartDate;
    private Date employmentEndDate;
    private String projectId;
    private String projectName;
    private Set<LeaveDTO> leaves;
    private String email;

    public static EmployeeDTO fromEmployee(Employee employee) {

        Department department = employee.getDepartment();
        Employee departmentHead = department.getDepartmentHead();
        return EmployeeDTO.builder()
                .id(employee.getId())
                .departmentId(department.getId())
                .departmentName(department.getName())
                .departHeadId(departmentHead != null ? departmentHead.getId() : null)
                .departHeadFullName(departmentHead != null ? departmentHead.getFullName() : null)
                .username(employee.getUser().getUsername())
                .roles(employee.getUser().getRoles().stream().map(item -> item.getName()).collect(Collectors.toList()))
                .fullName(employee.getFullName())
                .address(employee.getAddress())
                .contactNumber(employee.getContactNumber())
                .email(employee.getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .employmentStartDate(employee.getEmploymentStartDate())
                .employmentEndDate(employee.getEmploymentEndDate() != null ? employee.getEmploymentEndDate() : null)
                .projectId(employee.getProject() != null ? employee.getProject().getId() : null)
                .projectName(employee.getProject() != null ? employee.getProject().getName() : null)
                .build();
    }


}

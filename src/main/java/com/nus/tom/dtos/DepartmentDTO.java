package com.nus.tom.dtos;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import lombok.*;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//DepartmentResponse class that represents the response body for Department operations
public class DepartmentDTO {
    private String id;
    private String name;
    private String departHeadId;
    private String departHeadFullName;
    private String details;

    public static DepartmentDTO fromDepartment(Department department) {
        Employee departmentHead = department.getDepartmentHead();
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .details(department.getDetails())
                .departHeadId(departmentHead != null ? departmentHead.getId() : null)
                .departHeadFullName(departmentHead != null ? departmentHead.getFullName() : null)
                .build();
    }

    public static Department toDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());

        if (departmentDTO.getDepartHeadId() != null) {
            Employee departmentHead = new Employee();
            departmentHead.setId(departmentDTO.getDepartHeadId());
            departmentHead.setFullName(departmentDTO.getDepartHeadFullName());
            department.setDepartmentHead(departmentHead);
        }

        department.setDetails(departmentDTO.getDetails());

        return department;
    }

}


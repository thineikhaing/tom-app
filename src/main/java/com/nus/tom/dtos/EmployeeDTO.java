package com.nus.tom.dtos;

import com.nus.tom.model.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
public class EmployeeDTO {

    private String id;
    private DepartmentDTO department;
    private UserDTO user;
    private String fullName;
    private String address;
    private String contactNumber;
    private float leaveBalance;
    private Date dateOfBirth;
    private Date employment_startDate;
    private Date employment_endDate;
    private ProjectDTO project;
    private Set<LeaveDTO> leaves;

    private String email;

    public EmployeeDTO(Employee employee) {
    }


    // constructor with parameters

    public EmployeeDTO(String id, DepartmentDTO department, UserDTO user, String fullName, String address, String contactNumber, float leaveBalance, Date dateOfBirth, Date employment_startDate, Date employment_endDate, ProjectDTO project, Set<LeaveDTO> leaves) {
        this.id = id;
        this.department = department;
        this.user = user;
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.leaveBalance = leaveBalance;
        this.dateOfBirth = dateOfBirth;
        this.employment_startDate = employment_startDate;
        this.employment_endDate = employment_endDate;
        this.project = project;
        this.leaves = leaves;
    }

    // default constructor

    public EmployeeDTO() {
    }
}

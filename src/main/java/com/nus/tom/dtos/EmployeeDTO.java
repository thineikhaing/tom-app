package com.nus.tom.dtos;

import com.nus.tom.model.Employee;

import java.util.Date;
import java.util.Set;

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

    public EmployeeDTO(Employee employee) {
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public float getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(float leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getEmployment_startDate() {
        return employment_startDate;
    }

    public void setEmployment_startDate(Date employment_startDate) {
        this.employment_startDate = employment_startDate;
    }

    public Date getEmployment_endDate() {
        return employment_endDate;
    }

    public void setEmployment_endDate(Date employment_endDate) {
        this.employment_endDate = employment_endDate;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public Set<LeaveDTO> getLeaves() {
        return leaves;
    }

    public void setLeaves(Set<LeaveDTO> leaves) {
        this.leaves = leaves;
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

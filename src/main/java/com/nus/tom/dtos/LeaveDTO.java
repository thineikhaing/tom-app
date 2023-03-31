package com.nus.tom.dtos;

import com.nus.tom.model.Leave;

import java.time.Instant;

public class LeaveDTO {
    private String id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private String status;
    private String leaveType;
    private EmployeeDTO employee;

    public LeaveDTO(Leave leave) {
        this.id = leave.getId();
        this.name = leave.getName();
        this.startDate = leave.getStartDate();
        this.endDate = leave.getEndDate();
        this.status = leave.getStatus();
        this.leaveType = leave.getLeaveType();
        this.employee = new EmployeeDTO(leave.getEmployee());
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }
}
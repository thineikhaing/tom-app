package com.nus.tom.dtos;

import com.nus.tom.model.Leave;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
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


}
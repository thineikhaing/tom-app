package com.nus.tom.service.impl;

import com.nus.tom.model.Leave;
import com.nus.tom.model.enums.LeaveStatus;
import com.nus.tom.model.enums.LeaveType;
import com.nus.tom.repository.LeaveRepository;
import com.nus.tom.util.LeaveConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mya Pwint
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveUtil {

    private final LeaveRepository leaveRepository;

    private final LeaveConfig leaveConfig;

    public double getLeaveBalance(Leave leave) {

        List<Leave> approvedLeaves = leaveRepository.findByEmployeeIdAndLeaveTypeAndStatus(leave.getEmployee().getId(), leave.getLeaveType(), LeaveStatus.APPROVED.value);

        double approvedLeaveBalance = Optional.ofNullable(approvedLeaves).map(approvedLeave -> {
            return approvedLeave.stream().map(Leave::getRequestedDays).mapToDouble(Double::doubleValue).sum();
        }).orElse(0.0);

        Integer eligibleDaysByLeaveType = getTotalEligibleDaysByLeaveType(leave);

        if (Objects.nonNull(eligibleDaysByLeaveType) && approvedLeaveBalance <= eligibleDaysByLeaveType)
            return eligibleDaysByLeaveType - approvedLeaveBalance;
        return 0.0;

    }

    /**
     * get eligible leaves by leave type
     *
     * @param leave
     * @return return total no of days by leave type
     */
    private Integer getTotalEligibleDaysByLeaveType(Leave leave) {
        if (leave.getLeaveType().equalsIgnoreCase(LeaveType.ANNUAL.value)) {
            return leaveConfig.getMapping().get(LeaveType.ANNUAL.name().toLowerCase());
        }
        if (leave.getLeaveType().equalsIgnoreCase(LeaveType.MC.value)) {
            return leaveConfig.getMapping().get(LeaveType.MC.name().toLowerCase());
        }
        if (leave.getLeaveType().equalsIgnoreCase(LeaveType.CC.value)) {
            return leaveConfig.getMapping().get(LeaveType.CC.name().toLowerCase());
        }
        return 0;


    }
}

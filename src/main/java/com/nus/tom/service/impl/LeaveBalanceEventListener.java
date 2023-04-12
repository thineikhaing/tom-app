package com.nus.tom.service.impl;

import com.nus.tom.model.Leave;
import com.nus.tom.model.LeaveBalance;
import com.nus.tom.model.enums.LeaveStatus;
import com.nus.tom.repository.LeaveBalanceRepository;
import com.nus.tom.service.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mya Pwint
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveBalanceEventListener implements Listener {
    private final LeaveBalanceRepository leaveBalanceRepository;

    private final LeaveUtil leaveUtil;

    /**
     * listen leave subject and update leave balance
     *
     * @param subject
     */
    @Override
    public void listen(Object subject) {
        log.info("Leave Balance Event Listener is listening...");
        Leave leave = ((Leave) subject);
        /*update/insert leave balance in leave balance table*/

        if (LeaveStatus.APPROVED.value.equalsIgnoreCase(leave.getStatus())) {
            List<LeaveBalance> leaveBalances = leaveBalanceRepository.findByEmployeeIdAndLeaveType(leave.getEmployee().getId(), leave.getLeaveType());

            double approvedLeaveBalance = leaveUtil.getLeaveBalance(leave);

            if (!leaveBalances.isEmpty()) {
                leaveBalances.forEach(leaveBalance -> {
                    leaveBalance.setLeaveBalance(approvedLeaveBalance);
                });
                leaveBalanceRepository.saveAll(leaveBalances);
            } else {
                LeaveBalance leaveBalance = LeaveBalance.builder().leaveBalance(approvedLeaveBalance).leaveType(leave.getLeaveType()).employee(leave.getEmployee()).build();

                leaveBalanceRepository.save(leaveBalance);
            }

        }

    }
}

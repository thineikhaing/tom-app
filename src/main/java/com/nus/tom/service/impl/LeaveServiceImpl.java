package com.nus.tom.service.impl;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.model.enums.LeaveStatus;
import com.nus.tom.model.enums.LeaveType;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.LeaveRepository;
import com.nus.tom.service.LeaveService;
import com.nus.tom.util.LeaveConfig;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
    private final ResponseHelper responseHelper;

    private final LeaveConfig leaveConfig;


    /**
     * apply leave
     *
     * @param leave
     * @return
     */
    @Override
    public ResponseEntity<ResponseValueObject> save(Leave leave) {
        try {

            return checkAndSave(leave);


        } catch (Exception ex) {
            log.error("Exception in saving leave {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, leave.getId());
        }
    }

    @Override
    public ResponseEntity<ResponseValueObject> getLeaveBalance(String employeeId) {
        return null;
    }

    /**
     * search by leave id and update the status from approval/reject workflow
     *
     * @param leave
     * @return
     */
    @Override
    public ResponseEntity<ResponseValueObject> updateLeaveStatus(Leave leave) {
        return null;
    }

    /**
     * check employee exists? duplicate leave apply? leave balance valid?
     *
     * @param leave
     * @return
     */
    private ResponseEntity<ResponseValueObject> checkAndSave(Leave leave) {
        Employee employee = employeeRepository.findById(leave.getEmployee().getId()).orElse(null);

        if (Objects.isNull(employee))
            return responseHelper.setResponseEntity(TOMConstants.EMPLOYEE_NOT_EXIST, TOMConstants.EMPTY_STRING, leave.getId());

        if (checkDuplicateLeaves(leave))
            return responseHelper.setResponseEntity(TOMConstants.DUPLICATE_lEAVE, TOMConstants.EMPTY_STRING, leave.getId());

        if (checkValidRequest(leave) == Boolean.FALSE)
            return responseHelper.setResponseEntity(TOMConstants.LEAVE_BALANCE_LOWER, TOMConstants.EMPTY_STRING, leave.getId());

        leave.setStatus(LeaveStatus.PENDING.value);
        leaveRepository.save(leave);
        log.info("save leave for {}", leave.getEmployee().getId());
        return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, leave.getId());
    }

    /**
     * @param leave
     * @return
     */
    private boolean checkValidRequest(Leave leave) {
        if (leave.getEndDate().isAfter(leave.getStartDate())) {
            double diff = (ChronoUnit.MINUTES.between(leave.getStartDate(), leave.getEndDate()) / mph) / hpd;
            leave.setRequestedDays(diff);
            if (leave.getRequestedDays() > 0 && leave.getRequestedDays() <= getLeaveBalance(leave)) return true;
        }

        return false;
    }

    /**
     * calculate actual leave balance based on eligible leave balance by leave type and approved leave balance by employee
     *
     * @param leave
     * @return leave balance
     */
    private double getLeaveBalance(Leave leave) {

        List<Leave> approvedLeaves = leaveRepository.findByEmployeeIdAndLeaveTypeAndStatus(leave.getEmployee().getId(), leave.getLeaveType(), LeaveStatus.APPROVED.value);

        double approvedLeaveBalance = Optional.ofNullable(approvedLeaves).map(approvedLeave -> {
            return approvedLeave.stream().map(Leave::getRequestedDays).mapToDouble(Double::doubleValue).sum();
        }).orElse(0.0);

        Integer eligibleDaysByLeaveType = getTotalEligibleDaysByLeaveType(leave);

        if (Objects.nonNull(eligibleDaysByLeaveType) && approvedLeaveBalance <= eligibleDaysByLeaveType)
            return eligibleDaysByLeaveType - approvedLeaveBalance;
        return 0.0;

    }

    private boolean checkDuplicateLeaves(Leave leave) {
        List<Leave> duplicateLeaves = leaveRepository.findByEmployeeIdAndLeaveTypeAndStartDateAndEndDate(leave.getEmployee().getId(), leave.getLeaveType(), leave.getStartDate(), leave.getEndDate());

        return duplicateLeaves.isEmpty() ? false : true;
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
        return 0;
    }


}

package com.nus.tom.service.impl;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.model.enums.LeaveStatus;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.LeaveBalanceRepository;
import com.nus.tom.repository.LeaveRepository;
import com.nus.tom.service.LeaveService;
import com.nus.tom.util.JsonHandler;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
    private final ResponseHelper responseHelper;
    private final JsonHandler jsonHandler;
    private final LeaveBalanceRepository leaveBalanceRepository;

    private final LeaveUtil leaveUtil;

    private final EventManager eventManager;

    private final LeaveBalanceEventListener leaveBalanceEventListener;

    @PostConstruct
    public void init() {
        eventManager.bindListenerToPublisher(leaveBalanceEventListener);
    }

    @PreDestroy
    public void destroy() {
        eventManager.unBindListenerToPublisher(leaveBalanceEventListener);

    }

    /**
     * apply leave
     *
     * @param payload
     * @return
     */
    @Override
    public ResponseEntity<ResponseValueObject> save(String payload) {
        try {

            Leave leave = jsonHandler.fromJson(payload, Leave.class);

            return checkAndSave(leave);


        } catch (Exception ex) {
            log.error("Exception in saving leave {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, payload);
        }
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getLeaveBalance(String employeeId) {
        List<Map<String, Object>> leaveBalances = leaveBalanceRepository.findByEmployeeId(employeeId);
        if (leaveBalances.isEmpty())
            leaveBalances = new ArrayList<>();
        return new ResponseEntity<>(leaveBalances, HttpStatus.OK);
    }

    /**
     * search by leave id and update the status from approval/reject workflow
     *
     * @param payload
     * @return
     */
    @Override
    public ResponseEntity<ResponseValueObject> updateLeaveStatus(String payload) {
        try {
            Leave leave = jsonHandler.fromJson(payload, Leave.class);
            Leave leaveExisted = leaveRepository.findById(leave.getId()).get();

            if (!Objects.isNull(leaveExisted)) {
                leaveExisted.setStatus(leave.getStatus());
                Leave leaveUpdated = leaveRepository.save(leaveExisted);
                eventManager.getPublisher().publish(leaveUpdated);
            }

            return responseHelper.setResponseEntity(leave.getStatus(), TOMConstants.EMPTY_STRING, leave.getId());


        } catch (Exception ex) {
            log.error("Exception in saving leave {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, payload);
        }

    }

    /**
     * @param employeeId
     * @return leave requests
     */
    @Override
    public ResponseEntity<List<Map<String, Object>>> getLeaveRequests(String employeeId) {
        List<Map<String, Object>> leaves = leaveRepository.findByEmployeeId(employeeId);
        if (leaves.isEmpty())
            leaves = new ArrayList<>();
        return new ResponseEntity<>(leaves, HttpStatus.OK);
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
        log.info("saved leave for {}", leave.getEmployee().getId());

        log.info("sending email {}", employee.getFullName());
        /*Add Email Builder*/

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
            if (leave.getRequestedDays() > 0 && leave.getRequestedDays() <= leaveUtil.getLeaveBalance(leave))
                return true;
        }

        return false;
    }

    /**
     * calculate actual leave balance based on eligible leave balance by leave type and approved leave balance by employee
     *
     * @param leave
     * @return leave balance
     */


    private boolean checkDuplicateLeaves(Leave leave) {
        List<Leave> duplicateLeaves = leaveRepository.findByEmployeeIdAndLeaveTypeAndStartDateAndEndDate(leave.getEmployee().getId(), leave.getLeaveType(), leave.getStartDate(), leave.getEndDate());

        return duplicateLeaves.isEmpty() ? false : true;
    }


}
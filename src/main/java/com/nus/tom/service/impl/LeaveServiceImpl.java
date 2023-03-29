package com.nus.tom.service.impl;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.LeaveRepository;
import com.nus.tom.service.EmployeeService;
import com.nus.tom.service.LeaveService;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
    private final ResponseHelper responseHelper;

    /**
     * apply leave
     * @param leave
     * @return
     */
    @Override
    public ResponseEntity<ResponseValueObject> save(Leave leave) {
        try {
            //check employee exist?
            Employee employee = employeeRepository.findById(leave.getEmployee().getId()).orElse(null);
            if(Objects.nonNull(employee)){
                log.info("Save leave for {}", leave.getEmployee().getFullName());
                leaveRepository.save(leave);
                return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, leave.getId());
            }
            return responseHelper.setResponseEntity(TOMConstants.EMPLOYEE_NOT_EXIST, TOMConstants.EMPTY_STRING, leave.getId());
        } catch (Exception ex) {
            log.error("Exception in saving leave {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, leave.getId());
        }
    }

    @Override
    public ResponseEntity<ResponseValueObject> getLeaveBalance(String employeeId) {
        return null;
    }
}

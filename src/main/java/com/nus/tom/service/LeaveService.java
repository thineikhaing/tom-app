package com.nus.tom.service;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

public interface LeaveService {
    public static final  double mph = 60.0;
    public static final  double hpd = 24.0;
    ResponseEntity<ResponseValueObject> save(Leave leave);

    ResponseEntity<ResponseValueObject> getLeaveBalance(String employeeId);

    ResponseEntity<ResponseValueObject> updateLeaveStatus(Leave leave);


}

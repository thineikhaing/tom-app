package com.nus.tom.service;

import com.nus.tom.model.Leave;
import com.nus.tom.model.LeaveBalance;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LeaveService {
    public static final double mph = 60.0;
    public static final double hpd = 24.0;

    ResponseEntity<ResponseValueObject> save(String leave);

    ResponseEntity< List<Map<String,Object>>> getLeaveBalance(String employeeId);

    ResponseEntity<ResponseValueObject> updateLeaveStatus(String leave);


}

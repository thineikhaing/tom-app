package com.nus.tom.service;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

public interface LeaveService {

    ResponseEntity<ResponseValueObject> save(Leave leave);

    ResponseEntity<ResponseValueObject> getLeaveBalance(String employeeId);
}

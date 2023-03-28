package com.nus.tom.service.impl;

import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.EmployeeService;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ResponseHelper responseHelper;

    @Override
    public ResponseEntity<ResponseValueObject> save(Employee employee) {
        try {
            log.info("Save employee {}", employee.getFullName());
            employeeRepository.save(employee);
            return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, employee.getId());
        } catch (Exception ex) {
            log.error("Exception in saving employee {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, employee.getId());
        }
    }

}

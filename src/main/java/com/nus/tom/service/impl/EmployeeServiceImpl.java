package com.nus.tom.service.impl;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.EmployeeService;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;
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

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}

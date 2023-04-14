package com.nus.tom.service.impl;


import com.nus.tom.model.*;
import com.nus.tom.model.enums.ERole;
import com.nus.tom.model.enums.EmailStrategy;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.RoleRepository;
import com.nus.tom.repository.UserRepository;
import com.nus.tom.service.EmployeeService;
import com.nus.tom.util.ResourceNotFoundException;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.nus.tom.util.TOMConstants.EMPLOYEE_FTL;
import static com.nus.tom.util.TOMConstants.REGISTRATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ResponseHelper responseHelper;


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    private final LeaveUtil leaveUtil;

    @Value("${user.activateUrl}")
    private String activateUrl;

    @Override
    public ResponseEntity<ResponseValueObject> save(Employee employee) {
        try {
            log.info("Save employee {}", employee.getFullName());
            //test method only
            /*Insert Eligible Leaves*/
            employeeRepository.save(employee);
            leaveUtil.insertEligibleLeave(employee);
            return responseHelper.setResponseEntity(TOMConstants.SUCCESS, TOMConstants.EMPTY_STRING, employee.getId());
        } catch (Exception ex) {
            log.error("Exception in saving employee {}", ex.getStackTrace());
            return responseHelper.setResponseEntity(TOMConstants.ERROR, TOMConstants.EMPTY_STRING, employee.getId());
        }

    }


    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        log.info("Total number of employees: {}", employees.size());
        return employees;
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            log.info("Get Employee: {}", employee.get().getFullName());
            return employee.get();
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }
    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "email", email));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", employee.getDepartment().getId()));
        employee.setDepartment(department);
        Optional<User> existingUser = userRepository.findByEmail(employee.getEmail());

        if (existingUser.isPresent()) {
            employee.setUser(existingUser.get());
        }
        else{
            User user = new User();
            user.setUsername(employee.getFullName().replaceAll(" ", "_").toLowerCase());
            user.setEmail(employee.getEmail());
            user.setPassword(encoder.encode("password"));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);
            User newUser = userRepository.save(user);
            employee.setUser(newUser);
            log.info("Employee's user account: {}", newUser.getUsername());
        }

        log.info("Employee's department: {}",department.getName());
        log.info("Employee account: {}", employee.getEmail());

        invokeEmail(employee);
        Employee newemployee = employeeRepository.save(employee);
        leaveUtil.insertEligibleLeave(newemployee);

        return newemployee;
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", employee.getDepartment().getId()));

        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setContactNumber(employee.getContactNumber());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setEmploymentStartDate(employee.getEmploymentStartDate());
        existingEmployee.setEmploymentEndDate(employee.getEmploymentEndDate());
        existingEmployee.setDepartment(department);

        User exUser = existingEmployee.getUser();
        exUser.setEmail(employee.getEmail());
        User updatedUser = userRepository.save(exUser);
        existingEmployee.setUser(updatedUser);

        log.info("Updated Employee User email {}", existingEmployee.getUser().getEmail());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            log.info("Deleted Department ID {}", employee.get().getId());
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    @Override
    public List<Employee> getEmployeesByDepartmentID(String id) {
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        log.info("Get Employees By Department ID {}", employees.size());
        return employees;
    }

    /**
     * build email strategy and invoke
     *
     * @param employee
     */
    private void invokeEmail(Employee employee) {
        NotificationEvent notificationEvent = buildNotificationEvent(employee);
        emailService.invoke(notificationEvent);
    }

    private NotificationEvent buildNotificationEvent(Employee employee) {
        Set<String> recipients = new HashSet<>();
        recipients.add(employee.getEmail());
        return NotificationEvent.builder().employee(employee).subject(REGISTRATION).recipients(recipients).template(EMPLOYEE_FTL).type(EmailStrategy.REGISTRATION.type).activateUrl(activateUrl).build();
    }

}

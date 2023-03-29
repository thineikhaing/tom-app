package com.nus.tom.controller;

import com.nus.tom.dtos.*;
import com.nus.tom.model.*;
import com.nus.tom.payload.response.JwtResponse;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.EmployeeService;

import com.nus.tom.service.UserService;
import com.nus.tom.service.impl.UserDetailsImpl;
import com.nus.tom.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(path = "/saveEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<Employee> addEmployeeToDepartment(@RequestBody Employee employee, @PathVariable String departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        if (department == null) {
            throw new ResourceNotFoundException("Department", "id",  departmentId);

        }

        employee.setDepartment(department);
        Employee savedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Create
    @PostMapping("")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // Read All
    //retrieve a list of Employee entities from the database and convert them to EmployeeDTO objects before returning them to the client
    @GetMapping("")

    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employees.stream().map(employee -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setFullName(employee.getFullName());
            employeeDTO.setAddress(employee.getAddress());
            employeeDTO.setContactNumber(employee.getContactNumber());
            employeeDTO.setLeaveBalance(employee.getLeaveBalance());
            employeeDTO.setDateOfBirth(employee.getDateOfBirth());
            employeeDTO.setEmployment_startDate(employee.getEmployment_startDate());
            employeeDTO.setEmployment_endDate(employee.getEmployment_endDate());

            if (employee.getDepartment() != null) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(employee.getDepartment().getId());
                departmentDTO.setName(employee.getDepartment().getName());
                employeeDTO.setDepartment(departmentDTO);
            }

            if (employee.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(employee.getUser().getId());
                userDTO.setUsername(employee.getUser().getUsername());
                userDTO.setEmail(employee.getUser().getEmail());
                // Set user roles
                Set<Role> roles = employee.getUser().getRoles();
                if (roles != null) {
                    Set<RoleDTO> roleDTOs = new HashSet<>();
                    for (Role role : roles) {
                        RoleDTO roleDTO = new RoleDTO();
                        roleDTO.setId(role.getId());
                        roleDTO.setName(role.getName());
                        roleDTOs.add(roleDTO);
                    }
                    userDTO.setRoles(roleDTOs);
                }

                employeeDTO.setUser(userDTO);
            }

            if (employee.getProject() != null) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(employee.getProject().getId());
                projectDTO.setName(employee.getProject().getName());
                projectDTO.setStartDate(employee.getProject().getStartDate());
                projectDTO.setEndDate(employee.getProject().getEndDate());
                employeeDTO.setProject(projectDTO);
            }

            Set<LeaveDTO> leaveDTOs = new HashSet<>();
            if (employee.getLeaves() != null) {
                for (Leave leave : employee.getLeaves()) {
                    LeaveDTO leaveDTO = new LeaveDTO(leave);
                    leaveDTO.setId(leave.getId());
                    leaveDTO.setName(leave.getName());
                    leaveDTO.setStartDate(leave.getStartDate());
                    leaveDTO.setEndDate(leave.getEndDate());
                    leaveDTO.setStatus(leave.getStatus());
                    leaveDTO.setLeaveType(leave.getLeaveType());
                    leaveDTOs.add(leaveDTO);
                }
            }
            employeeDTO.setLeaves(leaveDTOs);

            return employeeDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(employeeDTOs);
    }


    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setContactNumber(employee.getContactNumber());
        employeeDTO.setLeaveBalance(employee.getLeaveBalance());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setEmployment_startDate(employee.getEmployment_startDate());
        employeeDTO.setEmployment_endDate(employee.getEmployment_endDate());

        if (employee.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(employee.getDepartment().getId());
            departmentDTO.setName(employee.getDepartment().getName());
            employeeDTO.setDepartment(departmentDTO);
        }

        if (employee.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(employee.getUser().getId());
            userDTO.setUsername(employee.getUser().getUsername());
            userDTO.setEmail(employee.getUser().getEmail());
            // Set user roles
            Set<Role> roles = employee.getUser().getRoles();
            if (roles != null) {
                Set<RoleDTO> roleDTOs = new HashSet<>();
                for (Role role : roles) {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setId(role.getId());
                    roleDTO.setName(role.getName());
                    roleDTOs.add(roleDTO);
                }
                userDTO.setRoles(roleDTOs);
            }

            employeeDTO.setUser(userDTO);
        }

        if (employee.getProject() != null) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(employee.getProject().getId());
            projectDTO.setName(employee.getProject().getName());
            projectDTO.setStartDate(employee.getProject().getStartDate());
            projectDTO.setEndDate(employee.getProject().getEndDate());
            employeeDTO.setProject(projectDTO);
        }

        Set<LeaveDTO> leaveDTOs = new HashSet<>();
        if (employee.getLeaves() != null) {
            for (Leave leave : employee.getLeaves()) {
                LeaveDTO leaveDTO = new LeaveDTO(leave);
                leaveDTO.setId(leave.getId());
                leaveDTO.setName(leave.getName());
                leaveDTO.setStartDate(leave.getStartDate());
                leaveDTO.setEndDate(leave.getEndDate());
                leaveDTO.setStatus(leave.getStatus());
                leaveDTO.setLeaveType(leave.getLeaveType());
                leaveDTOs.add(leaveDTO);
            }
        }
        employeeDTO.setLeaves(leaveDTOs);

        return ResponseEntity.ok().body(employeeDTO);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value = "id") String id,
                                                      @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        // Update basic employee information
        employee.setFullName(employeeDTO.getFullName());
        employee.setAddress(employeeDTO.getAddress());
        employee.setContactNumber(employeeDTO.getContactNumber());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());

        // Update department information
        if (employeeDTO.getDepartment() != null) {

            Department department = departmentService.getDepartmentById(employeeDTO.getDepartment().getId());
            if (department != null) {
                employee.setDepartment(department);
            }
        } else {
            employee.setDepartment(null);
        }

        // Update user information
        if (employeeDTO.getUser() != null) {


            UserDTO user = userService.getUserById(employeeDTO.getUser().getId());
            if (user != null) {
                user.setUsername(employeeDTO.getUser().getUsername());
                user.setEmail(employeeDTO.getUser().getEmail());

                // Update roles information
//                Set<Role> roles = new HashSet<>();
//                for (RoleDTO roleName : employeeDTO.getUser().getRoles()) {
//                    Role role = roleService.getRoleByName(ERole.valueOf(roleName));
//                    if (role != null) {
//                        roles.add(role);
//                    }
//                }
//                user.setRoles(roles);
//
//                employee.setUser(user);
            }
        } else {
            employee.setUser(null);
        }

        // Update project information
//        if (employeeDTO.getProjectId() != null) {
//            Project project = projectService.getProjectById(employeeDTO.getProjectId());
//            if (project != null) {
//                employee.setProject(project);
//            }
//        } else {
//            employee.setProject(null);
//        }
//
//        // Update leaves information
//        if (employeeDTO.getLeaveIds() != null) {
//            List<Leave> leaves = new ArrayList<>();
//            for (String leaveId : employeeDTO.getLeaveIds()) {
//                Leave leave = leaveService.getLeaveById(leaveId);
//                if (leave != null) {
//                    leaves.add(leave);
//                }
//            }
//            employee.setLeaves(leaves);
//        } else {
//            employee.setLeaves(null);
//        }

        employeeService.createEmployee(employee);
        EmployeeDTO updatedEmployeeDTO = employeeMapper.toDto(employee);
        return ResponseEntity.ok().body(updatedEmployeeDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }


}

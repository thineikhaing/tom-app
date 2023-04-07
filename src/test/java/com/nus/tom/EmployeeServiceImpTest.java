package com.nus.tom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.*;
import com.nus.tom.model.*;
import com.nus.tom.model.enums.ERole;
import com.nus.tom.model.enums.EmailStrategy;
import com.nus.tom.service.EmailBuilder;
import com.nus.tom.service.impl.EmailSender;
import com.nus.tom.service.impl.EmailService;
import com.nus.tom.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;

import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.repository.RoleRepository;
import com.nus.tom.repository.UserRepository;
import com.nus.tom.util.ResponseHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceImpTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private EmailService emailService;

    @Mock
    private ResponseHelper responseHelper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository, departmentRepository ,responseHelper,  userRepository, roleRepository, encoder, emailService);
    }

    @Test
    @Order(1)
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFullName("John Smith");
        employee.setEmail("john.smith@example.com");

        when(responseHelper.setResponseEntity(any(), any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(employeeRepository.save(any())).thenReturn(employee);

        ResponseEntity<ResponseValueObject> response = employeeService.save(employee);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(2)
    public void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setId("1");
        employee1.setFullName("John Smith");
        employee1.setEmail("john.smith@example.com");

        Employee employee2 = new Employee();
        employee2.setId("2");
        employee2.setFullName("Jane Doe");
        employee2.setEmail("jane.doe@example.com");

        employees.add(employee1);
        employees.add(employee2);

        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> response = employeeService.getAllEmployees();
        assertEquals(2, response.size());
        assertEquals(employee1.getId(), response.get(0).getId());
        assertEquals(employee2.getId(), response.get(1).getId());
    }

    @Test
    @Order(3)
    public void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFullName("John Smith");
        employee.setEmail("john.smith@example.com");

        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));

        Employee response = employeeService.getEmployeeById("1");

        assertEquals(employee.getId(), response.getId());
    }

    @Test
    @Order(4)
    void testCreateEmployee() {
        //create Department
        Department department = new Department();
        department.setId("123");
        department.setName("IT Department");

        //create Employee
        Employee employee = new Employee();
        employee.setId("456");
        employee.setFullName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setAddress("123 Main Street");
        employee.setContactNumber("+1-555-555-5555");
        employee.setLeaveBalance(10);
        employee.setDateOfBirth(new Date());
        employee.setEmploymentStartDate(new Date());
        employee.setEmploymentEndDate(null);
        employee.setDepartment(department);

        //create User role
        Role role = new Role();
        role.setId("111");
        role.setName(ERole.ROLE_USER);

        //create User
        User user = new User();
        user.setId("789");
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encoded_password");

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        when(encoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        verify(departmentRepository).findById(department.getId());
        verify(roleRepository).findByName(ERole.ROLE_USER);
        verify(encoder).encode("password");
        verify(userRepository).save(any(User.class));
        verify(employeeRepository).save(any(Employee.class));

        NotificationEvent notificationEvent = getNotification();
        notificationEvent.setEmployee(employee);
        verify(emailService).invoke(getNotification());

        Assertions.assertEquals(result, employee);
        Assertions.assertEquals(result.getUser().getId(), user.getId());

    }

    @Test
    @Order(5)
    public void testUpdateEmployee() {

        Department department = new Department();
        department.setId("123");
        department.setName("IT Department");

        //create Employee
        Employee employee = new Employee();
        employee.setId("456");
        employee.setFullName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setAddress("123 Main Street");
        employee.setContactNumber("+1-555-555-5555");
        employee.setLeaveBalance(10);
        employee.setDateOfBirth(new Date());
        employee.setEmploymentStartDate(new Date());
        employee.setEmploymentEndDate(null);
        employee.setDepartment(department);

        //create User role
        Role role = new Role();
        role.setId("111");
        role.setName(ERole.ROLE_USER);

        //create User
        User user = new User();
        user.setId("789");
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encoded_password");

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        when(encoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);



        Employee createdEmployee = employeeService.createEmployee(employee);
        assertNotNull(createdEmployee.getId());

        createdEmployee.setEmail("john.edit.doe@example.com");
        when(employeeRepository.findById(createdEmployee.getId())).thenReturn(Optional.of(createdEmployee));
        when(employeeRepository.save(createdEmployee)).thenReturn(createdEmployee);
        Employee updatedEmployee = employeeService.updateEmployee(createdEmployee.getId(), createdEmployee);
        assertNotNull(updatedEmployee);
        assertEquals(createdEmployee.getEmail(), updatedEmployee.getEmail());

    }

    @Test
    @Order(6)
    public void testDeleteEmployee() {
        Employee mockEmployee = new Employee();
        mockEmployee.setId("1");
        mockEmployee.setFullName("John Smith");
        mockEmployee.setEmail("john.smith@example.com");
        mockEmployee.setAddress("123 Main St");
        mockEmployee.setContactNumber("555-555-1234");
        mockEmployee.setLeaveBalance(10);
        mockEmployee.setDateOfBirth(new Date(1990, 1, 1));
        mockEmployee.setEmploymentStartDate(new Date(2021, 1, 1));
        mockEmployee.setEmploymentEndDate(new Date(2022, 1, 1));

        // mock the repository method
        when(employeeRepository.findById("1")).thenReturn(Optional.of(mockEmployee));

        // call the method to delete the employee
        employeeService.deleteEmployee("1");

        // verify that the repository method was called with the correct argument
        verify(employeeRepository, times(1)).findById("1");
        verify(employeeRepository, times(1)).deleteById("1");

    }

    @Test
    @Order(7)
    public void testGetEmployeesByDepartmentID() {
        when(employeeRepository.findByDepartmentId("1")).thenReturn(new ArrayList<>());
        List<Employee> employees = employeeService.getEmployeesByDepartmentID("1");
        assertNotNull(employees);
        assertTrue(employees.isEmpty());
    }
    private NotificationEvent getNotification() {
        return NotificationEvent.builder().build();
    }

}
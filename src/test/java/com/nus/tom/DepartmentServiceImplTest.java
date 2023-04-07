package com.nus.tom;

import com.nus.tom.model.Department;
import com.nus.tom.model.Employee;
import com.nus.tom.repository.DepartmentRepository;
import com.nus.tom.repository.EmployeeRepository;
import com.nus.tom.service.DepartmentService;
import com.nus.tom.service.impl.DepartmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentService = new DepartmentServiceImpl(departmentRepository, employeeRepository);
    }
    @Test
    @Order(1)
    public void testGetAllDepartments() {
        // Mock the repository to return a list of departments
        List<Department> departments = Arrays.asList(new Department(), new Department());
        when(departmentRepository.findAll()).thenReturn(departments);

        // Call the service method and verify the result
        List<Department> result = departmentService.getAllDepartments();
        assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    public void testGetDepartmentById() {
        // Mock the repository to return a department with the given ID
        String id = "1";
        Department department = new Department();
        department.setId(id);
        department.setName("HR Department");
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        // Call the service method and verify the result
        Department result = departmentService.getDepartmentById(id);
        assertNotNull(result);
    }

    @Test
    @Order(3)
    public void testCreateDepartment() {
        // Mock the repository to return the created department
        Department department = new Department();
        department.setId("1");
        department.setName("IT Department");
        when(departmentRepository.save(department)).thenReturn(department);

        // Call the service method and verify the result
        Department result = departmentService.createDepartment(department);
        assertNotNull(result);
    }

    @Test
    @Order(4)
    public void testUpdateDepartment() {
        // Mock the repository to return an existing department
        String id = "1";
        Department existingDepartment = new Department();
        existingDepartment.setId(id);
        existingDepartment.setName("Old Name");
        existingDepartment.setDetails("Old Details");
        when(departmentRepository.findById(id)).thenReturn(Optional.of(existingDepartment));

        Employee employee = new Employee();
        employee.setId("2");
        employee.setFullName("John Smith");
        employee.setEmail("john.smith@example.com");
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        // Call the service method with a new department object
        Department newDepartment = new Department();
        newDepartment.setName("New Name");
        newDepartment.setDetails("New Details");
        newDepartment.setDepartmentHead(employee);
        departmentService.updateDepartment(id, newDepartment);

        // Verify that the existing department has been updated
        verify(departmentRepository).save(existingDepartment);
        assertEquals("New Name", existingDepartment.getName());
        assertEquals("New Details", existingDepartment.getDetails());
        assertNotNull(existingDepartment.getDepartmentHead());
    }


    @Test
    @Order(5)
    public void testDeleteDepartment() {
        // Mock the repository to return an existing department
        String id = "1";
        Department department = new Department();
        department.setId("1");
        department.setName("Finance Department");
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        // Call the service method and verify that the department has been deleted
        departmentService.deleteDepartment(id);
        verify(departmentRepository).deleteById(id);
    }

    @Test
    @Order(6)
    void testAssignDepartmentHead() {
        // Create a department and an employee
        Department department = new Department();
        department.setId("1");
        department.setName("Test Department");
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFullName("Test Employee");

        // Mock the department repository and employee repository
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        // Create a department service and call the assignDepartmentHead method
        DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository, employeeRepository);
        departmentService.assignDepartmentHead(department.getId(), employee.getId());

        // Verify that the department's department head has been set to the employee
        assertEquals(employee, department.getDepartmentHead());

        // Verify that the department was saved
        verify(departmentRepository, times(1)).save(department);
    }



}

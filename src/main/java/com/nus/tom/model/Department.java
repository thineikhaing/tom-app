package com.nus.tom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department extends AuditableEntity implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "department_head_id", referencedColumnName = "id")
    private Employee departmentHead;

    @OneToMany(mappedBy = "department", fetch= FetchType.EAGER)
    private List<Employee> employees;

    private String details;

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }
}

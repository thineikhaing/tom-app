package com.nus.tom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends AuditableEntity implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String fullName;

    private String address;

    private String contactNumber;

    private float leaveBalance;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date employment_startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date employment_endDate;

}

package com.nus.tom.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "employee")
public class Employee extends AuditableEntity implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "employee", fetch= FetchType.EAGER)
    private Set<Leave> leaves;

}

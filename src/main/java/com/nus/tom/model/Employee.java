package com.nus.tom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String fullName;

    private String address;

    private String contactNumber;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date employmentStartDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date employmentEndDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "employee", fetch= FetchType.LAZY)
    private Set<Leave> leaves;

    @OneToMany(mappedBy = "employee", fetch= FetchType.LAZY)
    private Set<LeaveBalance> leaveBalances;

    @Size(max = 50)
    @Email
    private String email;

    public Employee(String john, String doe, String s) {
        super();
    }
}

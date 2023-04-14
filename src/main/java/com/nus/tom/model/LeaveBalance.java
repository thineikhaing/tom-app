package com.nus.tom.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

/**
 * @author Mya Pwint
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "leave_balance")
public class LeaveBalance extends AuditableEntity implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    @Column(name = "leaveBalance")
    private double leaveBalance;

    @Column(name = "leaveType")
    private String leaveType;
}

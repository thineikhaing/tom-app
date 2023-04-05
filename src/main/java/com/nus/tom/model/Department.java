package com.nus.tom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
//@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "department")
public class Department extends AuditableEntity implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "department_head_id", referencedColumnName = "id")
    private Employee departmentHead;

//    @OneToMany(mappedBy = "department", fetch= FetchType.EAGER)
//    private Set<Employee> employees;

    private String details;


    public Department(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public Department(String name, Employee departmentHead, String details) {
        this.name = name;
        this.departmentHead = departmentHead;
        this.details = details;
    }
//    public Department(String name, Employee departmentHead, Set<Employee> employees, String details) {
//    }


}

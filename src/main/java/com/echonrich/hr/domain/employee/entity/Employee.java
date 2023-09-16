package com.echonrich.hr.domain.employee.entity;

import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.job.entity.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity(name = "employees")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private Long employeeId;

    @Column(length = 20)
    private String firstName;

    @Column(length = 25, nullable = false)
    private String lastName;

    @Column(length = 25, nullable = false)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private Date hireDate;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job job;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal salary;

    @Column(precision = 2, scale = 2)
    private BigDecimal commissionPct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerId", referencedColumnName = "employeeId")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    public void updateSalary(BigDecimal salary) {
        if (salary == null) {
            return;
        }
        this.salary = salary;
    }
}

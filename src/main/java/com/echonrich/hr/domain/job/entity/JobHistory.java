package com.echonrich.hr.domain.job.entity;

import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(JobHistoryID.class)
public class JobHistory {
    @Id
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @Id
    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;
}

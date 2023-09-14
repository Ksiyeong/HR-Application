package com.echonrich.hr.domain.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "jobs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @Column(length = 10)
    private String jobId;

    @Column(length = 35, nullable = false)
    private String jobTitle;

    @Column(precision = 8, columnDefinition = "UNSIGNED")
    private BigDecimal minSalary;

    @Column(precision = 8, columnDefinition = "UNSIGNED")
    private BigDecimal maxSalary;
}

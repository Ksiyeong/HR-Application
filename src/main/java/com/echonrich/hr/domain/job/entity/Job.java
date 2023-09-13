package com.echonrich.hr.domain.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "jobs")
@Getter
@NoArgsConstructor
public class Job {
    @Id
    @Column(length = 10)
    private String jobId;

    @Column(length = 35, nullable = false)
    private String jobTitle;

    @Column(columnDefinition = "DECIMAL(8, 0) UNSIGNED")
    private Long minSalary;

    @Column(columnDefinition = "DECIMAL(8, 0) UNSIGNED")
    private Long maxSalary;
}

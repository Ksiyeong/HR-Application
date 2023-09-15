package com.echonrich.hr.domain.job.dto;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@Getter
public class JobHistoryResponseDto {
    private EmployeeDto.SimpleResponse employee;
    private Date startDate;
    private Date endDate;
    private JobDto.SimpleResponse job;
    private DepartmentDto.SimpleResponse department;

    @Builder
    @QueryProjection
    public JobHistoryResponseDto(EmployeeDto.SimpleResponse employee,
                                 Date startDate,
                                 Date endDate,
                                 JobDto.SimpleResponse job,
                                 DepartmentDto.SimpleResponse department) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.job = job;
        this.department = department;
    }
}

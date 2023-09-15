package com.echonrich.hr.domain.employee.dto;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.job.dto.JobDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

public class EmployeeDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long employeeId;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private Date hireDate;
        private JobDto.SimpleResponse job;
        private BigDecimal salary;
        private BigDecimal commissionPct;
        private SimpleResponse manager;
        private DepartmentDto.SimpleResponse department;
    }

    @NoArgsConstructor
    @Getter
    public static class SimpleResponse {
        private Long employeeId;
        private String firstName;
        private String lastName;

        @Builder
        @QueryProjection
        public SimpleResponse(Long employeeId,
                              String firstName,
                              String lastName) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}

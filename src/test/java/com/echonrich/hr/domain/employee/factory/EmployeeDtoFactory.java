package com.echonrich.hr.domain.employee.factory;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.job.dto.JobDto;

import java.math.BigDecimal;
import java.sql.Date;

public class EmployeeDtoFactory {
    public static EmployeeDto.Response createEmployeeResponseDto() {
        DepartmentDto.SimpleResponse department = DepartmentDto.SimpleResponse.builder()
                .departmentId(80L)
                .departmentName("Sales")
                .build();

        EmployeeDto.SimpleResponse manager = EmployeeDto.SimpleResponse.builder()
                .employeeId(100L)
                .firstName("Steven")
                .lastName("King")
                .build();

        JobDto.SimpleResponse job = JobDto.SimpleResponse.builder()
                .jobId("SA_MAN")
                .jobTitle("Sales Manager")
                .build();

        EmployeeDto.Response employee = EmployeeDto.Response.builder()
                .employeeId(145L)
                .firstName("John")
                .lastName("Russell")
                .email("JRUSSEL")
                .phoneNumber("011.44.1344.429268")
                .hireDate(Date.valueOf("1996-10-01"))
                .job(job)
                .salary(BigDecimal.valueOf(14000.00))
                .commissionPct(BigDecimal.valueOf(0.40))
                .manager(manager)
                .department(department)
                .build();

        return employee;
    }
}

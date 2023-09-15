package com.echonrich.hr.domain.job.factory;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.job.dto.JobDto;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;

public class JobHistoryResponseDtoFactory {
    public static Page<JobHistoryResponseDto> createJobHistoryResponseDtos(PageRequest pageRequest) {
        // employee
        EmployeeDto.SimpleResponse employee = EmployeeDto.SimpleResponse.builder()
                .employeeId(101L)
                .firstName("Neena")
                .lastName("Kochhar")
                .build();

        // job
        JobDto.SimpleResponse AC_ACCOUNT = JobDto.SimpleResponse.builder()
                .jobId("AC_ACCOUNT")
                .jobTitle("Public Accountant")
                .build();
        JobDto.SimpleResponse AC_MGR = JobDto.SimpleResponse.builder()
                .jobId("AC_MGR")
                .jobTitle("Accounting Manager")
                .build();

        // department
        DepartmentDto.SimpleResponse department = DepartmentDto.SimpleResponse.builder()
                .departmentId(110L)
                .departmentName("Accounting")
                .build();

        // jobHistory
        JobHistoryResponseDto response1 = JobHistoryResponseDto.builder()
                .employee(employee)
                .startDate(Date.valueOf("1989-09-21"))
                .endDate(Date.valueOf("1993-10-27"))
                .job(AC_ACCOUNT)
                .department(department)
                .build();

        JobHistoryResponseDto response2 = JobHistoryResponseDto.builder()
                .employee(employee)
                .startDate(Date.valueOf("1993-10-28"))
                .endDate(Date.valueOf("1997-03-15"))
                .job(AC_MGR)
                .department(department)
                .build();

        Page<JobHistoryResponseDto> result = new PageImpl<>(List.of(response1, response2), pageRequest, 2);
        return result;
    }
}

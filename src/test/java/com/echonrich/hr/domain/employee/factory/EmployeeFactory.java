package com.echonrich.hr.domain.employee.factory;

import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.job.entity.Job;

import java.math.BigDecimal;
import java.sql.Date;

public class EmployeeFactory {
    public static Employee createEmployee() {
        Department department = Department.builder()
                .departmentId(80L)
                .departmentName("Sales")
                .build();
        Employee manager = Employee.builder()
                .employeeId(100L)
                .firstName("Steven")
                .lastName("King")
                .build();
        Job job = Job.builder()
                .jobId("SA_MAN")
                .jobTitle("Sales Manager")
                .minSalary(new BigDecimal("20000.00"))
                .maxSalary(new BigDecimal("40000.00"))
                .build();
        Employee employee = Employee.builder()
                .employeeId(145L)
                .firstName("John")
                .lastName("Russell")
                .email("JRUSSEL")
                .phoneNumber("011.44.1344.429268")
                .hireDate(Date.valueOf("1996-10-01"))
                .job(job)
                .salary(new BigDecimal("24000.00"))
                .commissionPct(new BigDecimal("0.40"))
                .manager(manager)
                .department(department)
                .build();
        return employee;
    }
}

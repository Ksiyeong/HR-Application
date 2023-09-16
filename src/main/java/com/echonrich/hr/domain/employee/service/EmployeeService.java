package com.echonrich.hr.domain.employee.service;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    EmployeeDto.Response findEmployee(long employeeId);

    void updateSalaryForEmployees(List<Employee> employees, BigDecimal rate);

    void updateSalary(long employeeId, BigDecimal rate);
}

package com.echonrich.hr.domain.employee.service;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto.Response findEmployee(long employeeId);
}

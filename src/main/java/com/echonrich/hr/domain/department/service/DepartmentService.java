package com.echonrich.hr.domain.department.service;

import com.echonrich.hr.domain.department.dto.DepartmentDto;

public interface DepartmentService {
    DepartmentDto.Response findDepartment(long departmentId);
}

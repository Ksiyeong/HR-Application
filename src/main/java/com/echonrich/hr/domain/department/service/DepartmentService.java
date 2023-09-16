package com.echonrich.hr.domain.department.service;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;

public interface DepartmentService {
    DepartmentDto.Response findDepartment(long departmentId);

    Department findVerifiedDepartment(long departmentId);
}

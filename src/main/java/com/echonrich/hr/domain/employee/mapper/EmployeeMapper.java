package com.echonrich.hr.domain.employee.mapper;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto.Response toEmployeeResponseDto(Employee employee);
}
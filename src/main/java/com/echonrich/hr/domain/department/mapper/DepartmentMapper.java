package com.echonrich.hr.domain.department.mapper;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDto.Response toDepartmentResponseDto(Department department);
}

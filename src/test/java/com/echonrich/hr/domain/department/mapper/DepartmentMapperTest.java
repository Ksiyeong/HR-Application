package com.echonrich.hr.domain.department.mapper;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.department.factory.DepartmentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DepartmentMapperTest {
    private final DepartmentMapper departmentMapper = Mappers.getMapper(DepartmentMapper.class);

    @Test
    @DisplayName("toDepartmentResponseDto - 성공")
    void toDepartmentResponseDto() {
        // given
        Department department = DepartmentFactory.createDepartment();

        // when
        DepartmentDto.Response actual = departmentMapper.toDepartmentResponseDto(department);

        // then
        assertEquals(department.getDepartmentId(), actual.getDepartmentId());
        assertEquals(department.getDepartmentName(), actual.getDepartmentName());
        assertEquals(department.getManager().getEmployeeId(), actual.getManager().getEmployeeId());
        assertEquals(department.getManager().getFirstName(), actual.getManager().getFirstName());
        assertEquals(department.getManager().getLastName(), actual.getManager().getLastName());
        assertEquals(department.getLocation().getLocationId(), actual.getLocation().getLocationId());
        assertEquals(department.getLocation().getStreetAddress(), actual.getLocation().getStreetAddress());
        assertEquals(department.getLocation().getPostalCode(), actual.getLocation().getPostalCode());
        assertEquals(department.getLocation().getCity(), actual.getLocation().getCity());
        assertEquals(department.getLocation().getStateProvince(), actual.getLocation().getStateProvince());
        assertEquals(department.getLocation().getCountry().getCountryId(), actual.getLocation().getCountry().getCountryId());
        assertEquals(department.getLocation().getCountry().getCountryName(), actual.getLocation().getCountry().getCountryName());
        assertEquals(department.getLocation().getCountry().getRegion().getRegionId(), actual.getLocation().getCountry().getRegion().getRegionId());
        assertEquals(department.getLocation().getCountry().getRegion().getRegionName(), actual.getLocation().getCountry().getRegion().getRegionName());
    }

    @Test
    @DisplayName("toDepartmentResponseDto - Department : null")
    void toDepartmentResponseDto_null() {
        // given
        // when
        DepartmentDto.Response actual = departmentMapper.toDepartmentResponseDto(null);

        // then
        assertNull(actual);
    }
}
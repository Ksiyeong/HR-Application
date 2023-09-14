package com.echonrich.hr.domain.employee.mapper;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.employee.factory.EmployeeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmployeeMapperTest {
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    @DisplayName("toEmployeeResponseDto - 성공")
    void toEmployeeResponseDto() {
        // given
        Employee employee = EmployeeFactory.createEmployee();

        // when
        EmployeeDto.Response actual = employeeMapper.toEmployeeResponseDto(employee);

        // then
        assertEquals(employee.getEmployeeId(), actual.getEmployeeId());
        assertEquals(employee.getFirstName(), actual.getFirstName());
        assertEquals(employee.getLastName(), actual.getLastName());
        assertEquals(employee.getEmail(), actual.getEmail());
        assertEquals(employee.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(employee.getHireDate(), actual.getHireDate());
        assertEquals(employee.getJob().getJobId(), actual.getJob().getJobId());
        assertEquals(employee.getJob().getJobTitle(), actual.getJob().getJobTitle());
        assertEquals(employee.getSalary(), actual.getSalary());
        assertEquals(employee.getCommissionPct(), actual.getCommissionPct());
        assertEquals(employee.getManager().getEmployeeId(), actual.getManager().getEmployeeId());
        assertEquals(employee.getManager().getFirstName(), actual.getManager().getFirstName());
        assertEquals(employee.getManager().getLastName(), actual.getManager().getLastName());
        assertEquals(employee.getDepartment().getDepartmentId(), actual.getDepartment().getDepartmentId());
        assertEquals(employee.getDepartment().getDepartmentName(), actual.getDepartment().getDepartmentName());
    }

    @Test
    @DisplayName("toEmployeeResponseDto - Employee : null")
    void toEmployeeResponseDto_null() {
        // given
        // when
        EmployeeDto.Response actual = employeeMapper.toEmployeeResponseDto(null);

        // then
        assertNull(actual);
    }
}
package com.echonrich.hr.domain.employee.service;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.employee.factory.EmployeeDtoFactory;
import com.echonrich.hr.domain.employee.factory.EmployeeFactory;
import com.echonrich.hr.domain.employee.mapper.EmployeeMapper;
import com.echonrich.hr.domain.employee.repository.EmployeeRepository;
import com.echonrich.hr.global.exception.CustomLogicException;
import com.echonrich.hr.global.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;

    @Test
    @DisplayName("findEmployee - 성공")
    void findEmployee() {
        // given
        Employee employee = EmployeeFactory.createEmployee();
        long employeeId = employee.getEmployeeId(); // 145L
        EmployeeDto.Response expected = EmployeeDtoFactory.createEmployeeResponseDto();

        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee));
        given(employeeMapper.toEmployeeResponseDto(any())).willReturn(expected);

        // when
        EmployeeDto.Response actual = employeeService.findEmployee(employeeId);

        // then
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeMapper, times(1)).toEmployeeResponseDto(any());

        assertSame(expected, actual);
    }

    @Test
    @DisplayName("findEmployee - NOT_FOUND")
    void findEmployee_NOT_FOUND() {
        // given
        long employeeId = 145L;

        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        // then
        CustomLogicException thrown = assertThrows(CustomLogicException.class, () -> employeeService.findEmployee(employeeId));
        assertEquals(ExceptionCode.NOT_FOUND.getMessage(), thrown.getMessage());
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeMapper, never()).toEmployeeResponseDto(any());
    }
}
package com.echonrich.hr.domain.employee.service;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.employee.factory.EmployeeDtoFactory;
import com.echonrich.hr.domain.employee.factory.EmployeeFactory;
import com.echonrich.hr.domain.employee.mapper.EmployeeMapper;
import com.echonrich.hr.domain.employee.repository.EmployeeRepository;
import com.echonrich.hr.domain.job.entity.Job;
import com.echonrich.hr.global.exception.CustomLogicException;
import com.echonrich.hr.global.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    @DisplayName("updateSalary - 성공 : 정상 범위일 경우")
    void updateSalary() {
        // given
        Employee employee = EmployeeFactory.createEmployee();
        long employeeId = employee.getEmployeeId();
        BigDecimal rate = new BigDecimal("0.01");
        BigDecimal expectedSalary = employee.getSalary().multiply(BigDecimal.ONE.add(rate)).setScale(2, RoundingMode.UP);

        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee));

        // when
        employeeService.updateSalary(employeeId, rate);

        // then
        verify(employeeRepository, times(1)).findById(anyLong());
        assertEquals(expectedSalary, employee.getSalary());
    }

    @Test
    @DisplayName("updateSalary - 성공 : 최저 급여 보다 작을 경우")
    void updateSalary_minSalary() {
        // given
        Employee employee = EmployeeFactory.createEmployee();
        long employeeId = employee.getEmployeeId();
        BigDecimal rate = new BigDecimal("-1.0");

        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee));

        // when
        employeeService.updateSalary(employeeId, rate);

        // then
        verify(employeeRepository, times(1)).findById(anyLong());
        assertEquals(employee.getJob().getMinSalary(), employee.getSalary());
    }

    @Test
    @DisplayName("updateSalary - 성공 : 최대 급여 보다 클 경우")
    void updateSalary_maxSalary() {
        // given
        Employee employee = EmployeeFactory.createEmployee();
        long employeeId = employee.getEmployeeId();
        BigDecimal rate = new BigDecimal("1.0");

        given(employeeRepository.findById(anyLong())).willReturn(Optional.ofNullable(employee));

        // when
        employeeService.updateSalary(employeeId, rate);

        // then
        verify(employeeRepository, times(1)).findById(anyLong());
        assertEquals(employee.getJob().getMaxSalary(), employee.getSalary());
    }

    @Test
    @DisplayName("updateSalary - NOT_FOUND")
    void updateSalary_NOTFOUND() {
        // given
        long employeeId = 145L;
        BigDecimal rate = new BigDecimal("0.01");

        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        // then
        CustomLogicException thrown = assertThrows(CustomLogicException.class, () -> employeeService.updateSalary(employeeId, rate));
        assertEquals(ExceptionCode.NOT_FOUND.getMessage(), thrown.getMessage());
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("updateSalaryForEmployees - 성공 : 정상, 최저 급여")
    void updateSalaryForEmployees() {
        // given
        List<Employee> employees = new ArrayList<>();
        BigDecimal rate = new BigDecimal("-0.15");

        // 정상 범위
        Employee employee1 = Employee.builder()
                .employeeId(1L)
                .job(Job.builder()
                        .minSalary(new BigDecimal("5000.00"))
                        .maxSalary(new BigDecimal("10000.00")).build())
                .salary(new BigDecimal("8000.00"))
                .build();
        employees.add(employee1);
        BigDecimal expected1 = employee1.getSalary().multiply(BigDecimal.ONE.add(rate)).setScale(2, RoundingMode.UP);

        // 최저 급여
        Employee employee2 = Employee.builder()
                .employeeId(2L)
                .job(Job.builder()
                        .minSalary(new BigDecimal("6500.00"))
                        .maxSalary(new BigDecimal("10000.00")).build())
                .salary(new BigDecimal("7000.00"))
                .build();
        employees.add(employee2);

        when(employeeRepository.findById(employee1.getEmployeeId())).thenReturn(Optional.ofNullable(employee1));
        when(employeeRepository.findById(employee2.getEmployeeId())).thenReturn(Optional.ofNullable(employee2));

        // when
        employeeService.updateSalaryForEmployees(employees, rate);

        // then
        verify(employeeRepository, times(employees.size())).findById(anyLong());
        assertEquals(expected1, employee1.getSalary());
        assertEquals(employee2.getJob().getMinSalary(), employee2.getSalary());
    }

    @Test
    @DisplayName("updateSalaryForEmployees - 성공 : 정상, 최고 급여")
    void updateSalaryForEmployees_maxSalary() {
        // given
        List<Employee> employees = new ArrayList<>();
        BigDecimal rate = new BigDecimal("0.15");

        // 정상 범위
        Employee employee1 = Employee.builder()
                .employeeId(1L)
                .job(Job.builder()
                        .minSalary(new BigDecimal("5000.00"))
                        .maxSalary(new BigDecimal("10000.00")).build())
                .salary(new BigDecimal("8000.00"))
                .build();
        employees.add(employee1);
        BigDecimal expected1 = employee1.getSalary().multiply(BigDecimal.ONE.add(rate)).setScale(2, RoundingMode.UP);

        // 최저 급여
        Employee employee2 = Employee.builder()
                .employeeId(2L)
                .job(Job.builder()
                        .minSalary(new BigDecimal("6500.00"))
                        .maxSalary(new BigDecimal("8000.00")).build())
                .salary(new BigDecimal("7000.00"))
                .build();
        employees.add(employee2);

        when(employeeRepository.findById(employee1.getEmployeeId())).thenReturn(Optional.ofNullable(employee1));
        when(employeeRepository.findById(employee2.getEmployeeId())).thenReturn(Optional.ofNullable(employee2));

        // when
        employeeService.updateSalaryForEmployees(employees, rate);

        // then
        verify(employeeRepository, times(employees.size())).findById(anyLong());
        assertEquals(expected1, employee1.getSalary());
        assertEquals(employee2.getJob().getMaxSalary(), employee2.getSalary());
    }

    @Test
    @DisplayName("updateSalaryForEmployees - employees == null")
    void updateSalaryForEmployees_null() {
        // given
        BigDecimal rate = new BigDecimal("0.01");

        // when
        employeeService.updateSalaryForEmployees(null, rate);

        // then
        verifyNoInteractions(employeeRepository);
    }
}
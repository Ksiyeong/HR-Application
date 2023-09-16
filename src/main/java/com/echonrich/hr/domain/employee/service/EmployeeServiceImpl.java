package com.echonrich.hr.domain.employee.service;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.employee.mapper.EmployeeMapper;
import com.echonrich.hr.domain.employee.repository.EmployeeRepository;
import com.echonrich.hr.global.exception.CustomLogicException;
import com.echonrich.hr.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDto.Response findEmployee(long employeeId) {
        Employee employee = findVerifiedEmployee(employeeId);
        return employeeMapper.toEmployeeResponseDto(employee);
    }

    @Override
    public void updateSalary(long employeeId, BigDecimal rate) {
        Employee employee = findVerifiedEmployee(employeeId);
        BigDecimal maxSalary = employee.getJob().getMaxSalary();
        BigDecimal minSalary = employee.getJob().getMinSalary();

        // 임금 계산
        BigDecimal calculatedSalary = employee.getSalary().multiply(BigDecimal.ONE.add(rate));

        // 최저 급여 보다 작을 경우 최저로 설정
        if (calculatedSalary.compareTo(minSalary) < 0) {
            employee.updateSalary(minSalary);

            // 최고 급여 보다 클 경우 최고로 설정
        } else if (calculatedSalary.compareTo(maxSalary) > 0) {
            employee.updateSalary(maxSalary);

            // 정상 범위일 경우 소수점 둘 째자리 까지 올림 처리
        } else {
            employee.updateSalary(calculatedSalary.setScale(2, RoundingMode.UP));
        }
    }

    @Override
    public void updateSalaryForEmployees(List<Employee> employees, BigDecimal rate) {
        if (employees != null) {
            for (Employee employee : employees) {
                updateSalary(employee.getEmployeeId(), rate);
            }
        }
    }

    // 검증 로직
    @Transactional(readOnly = true)
    private Employee findVerifiedEmployee(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.NOT_FOUND));
    }
}

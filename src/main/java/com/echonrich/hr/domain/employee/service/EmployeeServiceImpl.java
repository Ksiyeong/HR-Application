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

    // 검증 로직
    private Employee findVerifiedEmployee(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.NOT_FOUND));
    }
}

package com.echonrich.hr.domain.department.service;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.department.mapper.DepartmentMapper;
import com.echonrich.hr.domain.department.repository.DepartmentRepository;
import com.echonrich.hr.global.exception.CustomLogicException;
import com.echonrich.hr.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto.Response findDepartment(long departmentId) {
        Department department = findVerifiedDepartment(departmentId);
        return departmentMapper.toDepartmentResponseDto(department);

    }

    // 검증 로직
    @Override
    @Transactional(readOnly = true)
    public Department findVerifiedDepartment(long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.NOT_FOUND));
    }
}

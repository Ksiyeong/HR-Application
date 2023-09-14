package com.echonrich.hr.domain.department.service;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.department.factory.DepartmentDtoFactory;
import com.echonrich.hr.domain.department.factory.DepartmentFactory;
import com.echonrich.hr.domain.department.mapper.DepartmentMapper;
import com.echonrich.hr.domain.department.repository.DepartmentRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @InjectMocks
    private DepartmentServiceImpl departmentService;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private DepartmentMapper departmentMapper;

    @Test
    @DisplayName("findDepartment - 성공")
    void findDepartment() {
        // given
        Department department = DepartmentFactory.createDepartment();
        long departmentId = department.getDepartmentId(); // 10L
        DepartmentDto.Response expected = DepartmentDtoFactory.createDepartmentResponseDto();

        given(departmentRepository.findById(anyLong())).willReturn(Optional.ofNullable(department));
        given(departmentMapper.toDepartmentResponseDto(any())).willReturn(expected);

        // when
        DepartmentDto.Response actual = departmentService.findDepartment(departmentId);

        // then
        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentMapper, times(1)).toDepartmentResponseDto(any());

        assertSame(expected, actual);
    }

    @Test
    @DisplayName("findDepartment - NOT_FOUND")
    void findDepartment_NOT_FOUND() {
        // given
        long departmentId = 10L;

        given(departmentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        // then
        CustomLogicException thrown = assertThrows(CustomLogicException.class, () -> departmentService.findDepartment(departmentId));
        assertEquals(ExceptionCode.NOT_FOUND.getMessage(), thrown.getMessage());
        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentMapper, never()).toDepartmentResponseDto(any());
    }
}
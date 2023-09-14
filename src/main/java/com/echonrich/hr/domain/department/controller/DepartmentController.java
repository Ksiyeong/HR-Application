package com.echonrich.hr.domain.department.controller;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.service.DepartmentService;
import com.echonrich.hr.global.response.SingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public ResponseEntity getDepartment(@Positive @PathVariable long departmentId) {
        DepartmentDto.Response response = departmentService.findDepartment(departmentId);
        return ResponseEntity.ok(new SingleResponse<>(response));
    }
}

package com.echonrich.hr.domain.department.controller;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.department.service.DepartmentService;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.service.EmployeeService;
import com.echonrich.hr.global.response.SingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @GetMapping("/{departmentId}")
    public ResponseEntity getDepartment(@PathVariable @Positive long departmentId) {
        DepartmentDto.Response response = departmentService.findDepartment(departmentId);
        return ResponseEntity.ok(new SingleResponse<>(response));
    }

    @PatchMapping("/{departmentId}/employees/salary")
    public ResponseEntity patchSalaryByDepartmentId(@PathVariable @Positive long departmentId,
                                                    @RequestBody EmployeeDto.SalaryRequest requestBody) {
        Department department = departmentService.findVerifiedDepartment(departmentId);
        employeeService.updateSalaryForEmployees(department.getEmployees(), requestBody.getRate());
        return ResponseEntity.noContent().build();
    }

}

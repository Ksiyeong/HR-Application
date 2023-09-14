package com.echonrich.hr.domain.employee.controller;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity getEmployee(@Positive @PathVariable long employeeId) {
        EmployeeDto.Response response = employeeService.findEmployee(employeeId);
        return ResponseEntity.ok(new SingleResponse<>(response));
    }
}

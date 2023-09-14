package com.echonrich.hr.domain.department.dto;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DepartmentDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long departmentId;
        private String departmentName;
        private EmployeeDto.SimpleResponse manager;
        private LocationDto.Response location;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class SimpleResponse {
        private Long departmentId;
        private String departmentName;
    }
}

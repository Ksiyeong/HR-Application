package com.echonrich.hr.domain.department.dto;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.location.dto.LocationDto;
import com.querydsl.core.annotations.QueryProjection;
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

    @NoArgsConstructor
    @Getter
    public static class SimpleResponse {
        private Long departmentId;
        private String departmentName;

        @Builder
        @QueryProjection
        public SimpleResponse(Long departmentId,
                              String departmentName) {
            this.departmentId = departmentId;
            this.departmentName = departmentName;
        }
    }
}

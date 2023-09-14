package com.echonrich.hr.domain.department.factory;

import com.echonrich.hr.domain.country.dto.CountryDto;
import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.location.dto.LocationDto;
import com.echonrich.hr.domain.region.dto.RegionDto;

public class DepartmentDtoFactory {
    public static DepartmentDto.Response createDepartmentResponseDto() {
        // 사용시 Id값 고정 확인 잘할것
        EmployeeDto.SimpleResponse manager = EmployeeDto.SimpleResponse.builder()
                .employeeId(200L)
                .firstName("Jennifer")
                .lastName("Whalen")
                .build();
        RegionDto.Response region = RegionDto.Response.builder()
                .regionId(2L)
                .regionName("Americas")
                .build();
        CountryDto.Response country = CountryDto.Response.builder()
                .countryId("US")
                .countryName("United States of America")
                .region(region)
                .build();
        LocationDto.Response location = LocationDto.Response.builder()
                .locationId(1700L)
                .streetAddress("2004 Charade Rd")
                .postalCode("98199")
                .city("Seattle")
                .stateProvince("Washington")
                .country(country)
                .build();
        DepartmentDto.Response departmentResponseDto = DepartmentDto.Response.builder()
                .departmentId(10L)
                .departmentName("Administration")
                .manager(manager)
                .location(location)
                .build();
        return departmentResponseDto;
    }
}

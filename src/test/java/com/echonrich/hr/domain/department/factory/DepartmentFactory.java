package com.echonrich.hr.domain.department.factory;

import com.echonrich.hr.domain.country.entity.Country;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.location.entity.Location;
import com.echonrich.hr.domain.region.entity.Region;

public class DepartmentFactory {
    // 사용시 Id값 고정 확인 잘할것
    public static Department createDepartment() {
        Employee manager = Employee.builder()
                .employeeId(200L)
                .firstName("Jennifer")
                .lastName("Whalen")
                .build();
        Region region = Region.builder()
                .regionId(2L)
                .regionName("Americas")
                .build();
        Country country = Country.builder()
                .countryId("US")
                .countryName("United States of America")
                .region(region)
                .build();
        Location location = Location.builder()
                .locationId(1700L)
                .streetAddress("2004 Charade Rd")
                .postalCode("98199")
                .city("Seattle")
                .stateProvince("Washington")
                .country(country)
                .build();
        Department department = Department.builder()
                .departmentId(10L)
                .departmentName("Administration")
                .manager(manager)
                .location(location)
                .build();

        return department;
    }
}

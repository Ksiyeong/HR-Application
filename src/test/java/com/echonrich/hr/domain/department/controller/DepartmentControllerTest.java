package com.echonrich.hr.domain.department.controller;

import com.echonrich.hr.domain.department.dto.DepartmentDto;
import com.echonrich.hr.domain.department.entity.Department;
import com.echonrich.hr.domain.department.factory.DepartmentDtoFactory;
import com.echonrich.hr.domain.department.factory.DepartmentFactory;
import com.echonrich.hr.domain.department.service.DepartmentService;
import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("getDepartment - 성공")
    void getDepartment() throws Exception {
        // given
        DepartmentDto.Response response = DepartmentDtoFactory.createDepartmentResponseDto();
        long departmentId = response.getDepartmentId(); // 10L

        given(departmentService.findDepartment(anyLong())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/departments/{departmentId}", departmentId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.departmentId").value(response.getDepartmentId()))
                .andExpect(jsonPath("$.data.departmentName").value(response.getDepartmentName()))
                .andExpect(jsonPath("$.data.manager.employeeId").value(response.getManager().getEmployeeId()))
                .andExpect(jsonPath("$.data.manager.firstName").value(response.getManager().getFirstName()))
                .andExpect(jsonPath("$.data.manager.lastName").value(response.getManager().getLastName()))
                .andExpect(jsonPath("$.data.location.locationId").value(response.getLocation().getLocationId()))
                .andExpect(jsonPath("$.data.location.streetAddress").value(response.getLocation().getStreetAddress()))
                .andExpect(jsonPath("$.data.location.postalCode").value(response.getLocation().getPostalCode()))
                .andExpect(jsonPath("$.data.location.city").value(response.getLocation().getCity()))
                .andExpect(jsonPath("$.data.location.stateProvince").value(response.getLocation().getStateProvince()))
                .andExpect(jsonPath("$.data.location.country.countryId").value(response.getLocation().getCountry().getCountryId()))
                .andExpect(jsonPath("$.data.location.country.countryName").value(response.getLocation().getCountry().getCountryName()))
                .andExpect(jsonPath("$.data.location.country.region.regionId").value(response.getLocation().getCountry().getRegion().getRegionId()))
                .andExpect(jsonPath("$.data.location.country.region.regionName").value(response.getLocation().getCountry().getRegion().getRegionName()));
    }

    @Test
    @DisplayName("getDepartment - id값이 0이하인 경우")
    void getDepartment_id_0() throws Exception {
        // given
        long departmentId = 0L;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/departments/{departmentId}", departmentId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("patchSalaryByDepartmentId - 성공")
    void patchSalaryByDepartmentId() throws Exception {
        // given
        Department department = DepartmentFactory.createDepartment();
        Long departmentId = department.getDepartmentId();

        EmployeeDto.SalaryRequest requestBody = EmployeeDto.SalaryRequest.builder()
                .rate(new BigDecimal("0.01"))
                .build();

        given(departmentService.findVerifiedDepartment(anyLong())).willReturn(department);
        doNothing().when(employeeService).updateSalaryForEmployees(anyList(), any());

        String content = objectMapper.writeValueAsString(requestBody);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/api/v1/departments/{departmentId}/employees/salary", departmentId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("patchSalaryByDepartmentId - id값이 0이하인 경우")
    void patchSalaryByDepartmentId_id_0() throws Exception {
        // given
        Long departmentId = 0L;

        EmployeeDto.SalaryRequest requestBody = EmployeeDto.SalaryRequest.builder()
                .rate(new BigDecimal("0.01"))
                .build();

        String content = objectMapper.writeValueAsString(requestBody);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/api/v1/departments/{departmentId}/employees/salary", departmentId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("patchSalaryByDepartmentId - rate : null")
    void patchSalaryByDepartmentId_rate_null() throws Exception {
        // given
        Long departmentId = 1L;

        EmployeeDto.SalaryRequest requestBody = EmployeeDto.SalaryRequest.builder()
                .rate(null)
                .build();

        String content = objectMapper.writeValueAsString(requestBody);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/api/v1/departments/{departmentId}/employees/salary", departmentId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }
}
package com.echonrich.hr.domain.employee.controller;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.factory.EmployeeDtoFactory;
import com.echonrich.hr.domain.employee.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("getEmployee - 성공")
    void getEmployee() throws Exception {
        // given
        EmployeeDto.Response response = EmployeeDtoFactory.createEmployeeResponseDto();
        long employeeId = response.getEmployeeId(); // 145L

        given(employeeService.findEmployee(anyLong())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/employees/{employeeId}", employeeId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.employeeId").value(response.getEmployeeId()))
                .andExpect(jsonPath("$.data.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.phoneNumber").value(response.getPhoneNumber()))
                .andExpect(jsonPath("$.data.hireDate").value(response.getHireDate().toString()))
                .andExpect(jsonPath("$.data.job.jobId").value(response.getJob().getJobId()))
                .andExpect(jsonPath("$.data.job.jobTitle").value(response.getJob().getJobTitle()))
                .andExpect(jsonPath("$.data.salary").value(response.getSalary()))
                .andExpect(jsonPath("$.data.commissionPct").value(response.getCommissionPct()))
                .andExpect(jsonPath("$.data.manager.employeeId").value(response.getManager().getEmployeeId()))
                .andExpect(jsonPath("$.data.manager.firstName").value(response.getManager().getFirstName()))
                .andExpect(jsonPath("$.data.manager.lastName").value(response.getManager().getLastName()))
                .andExpect(jsonPath("$.data.department.departmentId").value(response.getDepartment().getDepartmentId()))
                .andExpect(jsonPath("$.data.department.departmentName").value(response.getDepartment().getDepartmentName()));
    }

    @Test
    @DisplayName("getEmployee - id값이 0이하인 경우")
    void getEmployee_id_0() throws Exception {
        // given
        long employeeId = 0L;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/employees/{employeeId}", employeeId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }
}
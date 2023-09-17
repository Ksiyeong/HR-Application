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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
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
                RestDocumentationRequestBuilders.get("/api/v1/departments/{departmentId}", departmentId)
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
                .andExpect(jsonPath("$.data.location.country.region.regionName").value(response.getLocation().getCountry().getRegion().getRegionName()))
                .andDo(document(
                        "getDepartment",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("departmentId").description("부서 식별자")
                                .attributes(key("type").value("Number"))
                                .attributes(key("constraints").value("1이상 4294967295이하"))),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터 객체"),
                                fieldWithPath("data.departmentId").type(JsonFieldType.NUMBER).description("부서 식별자"),
                                fieldWithPath("data.departmentName").type(JsonFieldType.STRING).description("부서명"),
                                fieldWithPath("data.manager").type(JsonFieldType.OBJECT).description("부서 매니저 객체"),
                                fieldWithPath("data.manager.employeeId").type(JsonFieldType.NUMBER).description("매니저 식별자"),
                                fieldWithPath("data.manager.firstName").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("data.manager.lastName").type(JsonFieldType.STRING).description("매니저 성"),
                                fieldWithPath("data.location").type(JsonFieldType.OBJECT).description("위치 객체"),
                                fieldWithPath("data.location.locationId").type(JsonFieldType.NUMBER).description("위치 식별자"),
                                fieldWithPath("data.location.streetAddress").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("data.location.postalCode").type(JsonFieldType.STRING).description("우편번호"),
                                fieldWithPath("data.location.city").type(JsonFieldType.STRING).description("도시"),
                                fieldWithPath("data.location.stateProvince").type(JsonFieldType.STRING).description("주"),
                                fieldWithPath("data.location.country").type(JsonFieldType.OBJECT).description("국가 객체"),
                                fieldWithPath("data.location.country.countryId").type(JsonFieldType.STRING).description("국가 식별자"),
                                fieldWithPath("data.location.country.countryName").type(JsonFieldType.STRING).description("국가명"),
                                fieldWithPath("data.location.country.region").type(JsonFieldType.OBJECT).description("지역 객체"),
                                fieldWithPath("data.location.country.region.regionId").type(JsonFieldType.NUMBER).description("지역 식별자"),
                                fieldWithPath("data.location.country.region.regionName").type(JsonFieldType.STRING).description("지역명")
                        )
                ));
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
                RestDocumentationRequestBuilders.patch("/api/v1/departments/{departmentId}/employees/salary", departmentId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "patchSalaryByDepartmentId",
                        preprocessRequest(prettyPrint()),
                        pathParameters(parameterWithName("departmentId").description("부서 식별자")
                                .attributes(key("type").value("Number"))
                                .attributes(key("constraints").value("1이상 4294967295이하"))),
                        requestFields(fieldWithPath("rate").type(JsonFieldType.NUMBER).description("인상 비율"))
                ))
        ;
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
    @DisplayName("patchSalaryByDepartmentId - id값이 4294967296 이상인 경우")
    void patchSalaryByDepartmentId_id_4294967296() throws Exception {
        // given
        Long departmentId = 4294967296L;

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
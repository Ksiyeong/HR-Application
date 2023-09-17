package com.echonrich.hr.domain.employee.controller;

import com.echonrich.hr.domain.employee.dto.EmployeeDto;
import com.echonrich.hr.domain.employee.factory.EmployeeDtoFactory;
import com.echonrich.hr.domain.employee.service.EmployeeService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
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
                RestDocumentationRequestBuilders.get("/api/v1/employees/{employeeId}", employeeId)
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
                .andExpect(jsonPath("$.data.department.departmentName").value(response.getDepartment().getDepartmentName()))
                .andDo(document(
                        "getEmployee",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("employeeId").description("직원 식별자")
                                .attributes(key("type").value("Number"))
                                .attributes(key("constraints").value("1이상 4294967295이하"))),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("직원 객체"),
                                fieldWithPath("data.employeeId").type(JsonFieldType.NUMBER).description("직원 식별자"),
                                fieldWithPath("data.firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("data.hireDate").type(JsonFieldType.STRING).description("채용일"),
                                fieldWithPath("data.job").type(JsonFieldType.OBJECT).description("직책 객체"),
                                fieldWithPath("data.job.jobId").type(JsonFieldType.STRING).description("직책 식별자"),
                                fieldWithPath("data.job.jobTitle").type(JsonFieldType.STRING).description("직책명"),
                                fieldWithPath("data.salary").type(JsonFieldType.NUMBER).description("급여"),
                                fieldWithPath("data.commissionPct").type(JsonFieldType.NUMBER).description("커미션"),
                                fieldWithPath("data.manager").type(JsonFieldType.OBJECT).description("매니저 객체"),
                                fieldWithPath("data.manager.employeeId").type(JsonFieldType.NUMBER).description("매니저 식별자"),
                                fieldWithPath("data.manager.firstName").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("data.manager.lastName").type(JsonFieldType.STRING).description("매니저 성"),
                                fieldWithPath("data.department").type(JsonFieldType.OBJECT).description("부서 객체"),
                                fieldWithPath("data.department.departmentId").type(JsonFieldType.NUMBER).description("부서 식별자"),
                                fieldWithPath("data.department.departmentName").type(JsonFieldType.STRING).description("부서명"))
                ));
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

    @Test
    @DisplayName("getEmployee - id값이 4294967296 이상인 경우")
    void getEmployee_id_4294967296() throws Exception {
        // given
        long employeeId = 4294967296L;

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
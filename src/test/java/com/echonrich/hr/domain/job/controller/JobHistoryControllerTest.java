package com.echonrich.hr.domain.job.controller;

import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.factory.JobHistoryResponseDtoFactory;
import com.echonrich.hr.domain.job.service.JobHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(JobHistoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
class JobHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JobHistoryService jobHistoryService;

    @Test
    @DisplayName("getJobHistories - 성공")
    void getJobHistories() throws Exception {
        // given
        Long employee_id = 101L;
        Integer page = 1;
        Integer size = 10;
        Page<JobHistoryResponseDto> response = JobHistoryResponseDtoFactory.createJobHistoryResponseDtos(PageRequest.of(page - 1, size));

        given(jobHistoryService.findJobHistories(any(), any())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("employee_id", String.valueOf(employee_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(response.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(response.getTotalPages()))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document(
                        "getJobHistories",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("employee_id").description("직원 식별자").optional()
                                        .attributes(key("constraints").value("1이상 4294967295이하"))
                                        .attributes(key("type").value("Number")),
                                parameterWithName("start_date").description("직책 시작 날짜").optional()
                                        .attributes(key("type").value("String"))
                                        .attributes(key("constraints").value("yyyy-MM-dd")),
                                parameterWithName("end_date").description("직책 종료 날짜").optional()
                                        .attributes(key("type").value("String"))
                                        .attributes(key("constraints").value("yyyy-MM-dd")),
                                parameterWithName("job_id").description("직책 식별자").optional()
                                        .attributes(key("type").value("String")),
                                parameterWithName("department_id").description("부서 식별자").optional()
                                        .attributes(key("constraints").value("1이상 4294967295이하"))
                                        .attributes(key("type").value("Number")),
                                parameterWithName("page").description("요청할 페이지").optional()
                                        .attributes(key("default").value("1"))
                                        .attributes(key("constraints").value("1이상"))
                                        .attributes(key("type").value("Number")),
                                parameterWithName("size").description("페이지 당 데이터 갯수").optional()
                                        .attributes(key("constraints").value("1이상"))
                                        .attributes(key("default").value("10"))
                                        .attributes(key("type").value("Number")),
                                parameterWithName("direction").description("정렬 방법(ASC, DESC)").optional()
                                        .attributes(key("default").value("ASC"))
                                        .attributes(key("type").value("String")),
                                parameterWithName("sort").description("정렬 기준(페이징값들을 제외한 위 5종)").optional()
                                        .attributes(key("default").value("employee_id"))
                                        .attributes(key("type").value("String"))),
                        responseFields(
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 당 데이터 갯수"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[*].employee").type(JsonFieldType.OBJECT).description("직원"),
                                fieldWithPath("data[*].employee.employeeId").type(JsonFieldType.NUMBER).description("직원 식별자"),
                                fieldWithPath("data[*].employee.firstName").type(JsonFieldType.STRING).description("직원 이름"),
                                fieldWithPath("data[*].employee.lastName").type(JsonFieldType.STRING).description("직원 성"),
                                fieldWithPath("data[*].startDate").type(JsonFieldType.STRING).description("직책 시작 날짜"),
                                fieldWithPath("data[*].endDate").type(JsonFieldType.STRING).description("직책 종료 날짜"),
                                fieldWithPath("data[*].job").type(JsonFieldType.OBJECT).description("직책"),
                                fieldWithPath("data[*].job.jobId").type(JsonFieldType.STRING).description("직책 식별자"),
                                fieldWithPath("data[*].job.jobTitle").type(JsonFieldType.STRING).description("직책명"),
                                fieldWithPath("data[*].department").type(JsonFieldType.OBJECT).description("부서"),
                                fieldWithPath("data[*].department.departmentId").type(JsonFieldType.NUMBER).description("부서 식별자"),
                                fieldWithPath("data[*].department.departmentName").type(JsonFieldType.STRING).description("부서명")
                        )));
    }

    @Test
    @DisplayName("getJobHistories - employee_id가 0 이하인 경우")
    void getJobHistories_employee_id_0() throws Exception {
        // given
        Long employee_id = 0L;
        Integer page = 1;
        Integer size = 10;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("employee_id", String.valueOf(employee_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - employee_id가 4294967296 이상인 경우")
    void getJobHistories_employee_id_4294967296() throws Exception {
        // given
        Long employee_id = 4294967296L;
        Integer page = 1;
        Integer size = 10;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("employee_id", String.valueOf(employee_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - department_id가 0 이하인 경우")
    void getJobHistories_department_id_0() throws Exception {
        // given
        Long department_id = 0L;
        Integer page = 1;
        Integer size = 10;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("department_id", String.valueOf(department_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - department_id가 4294967296 이상인 경우")
    void getJobHistories_department_id_4294967296() throws Exception {
        // given
        Long department_id = 4294967296L;
        Integer page = 1;
        Integer size = 10;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("department_id", String.valueOf(department_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - page가 0이하인 경우")
    void getJobHistories_page_0() throws Exception {
        // given
        Long employee_id = 101L;
        Integer page = 0;
        Integer size = 10;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("employee_id", String.valueOf(employee_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - size가 0이하인 경우")
    void getJobHistories_size_0() throws Exception {
        // given
        Long employee_id = 101L;
        Integer page = 1;
        Integer size = 0;

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("employee_id", String.valueOf(employee_id))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - 잘못된 start_date")
    void getJobHistories_start_date_wrong() throws Exception {
        // given
        String start_date = "1997-05-33";

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("start_date", start_date)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getJobHistories - 잘못된 end_date")
    void getJobHistories_end_date_wrong() throws Exception {
        // given
        String end_date = "1997-15-23";

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/job-histories")
                        .param("end_date", end_date)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isBadRequest());
    }
}
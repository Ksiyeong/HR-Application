package com.echonrich.hr.domain.job.controller;

import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.factory.JobHistoryResponseDtoFactory;
import com.echonrich.hr.domain.job.service.JobHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(jsonPath("$.data").isArray());
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
}
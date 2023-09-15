package com.echonrich.hr.domain.job.controller;

import com.echonrich.hr.domain.job.dto.JobHistoryParams;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.service.JobHistoryService;
import com.echonrich.hr.global.common.PageParams;
import com.echonrich.hr.global.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/job-histories")
public class JobHistoryController {
    private final JobHistoryService jobHistoryService;

    @GetMapping
    public ResponseEntity getJobHistories(@ModelAttribute @Valid JobHistoryParams jobHistoryParams,
                                          @ModelAttribute @Valid PageParams pageParams) {
        Page<JobHistoryResponseDto> jobHistory = jobHistoryService.findJobHistories(jobHistoryParams, pageParams.toPageRequest());
        return ResponseEntity.ok(new PageResponse<>(jobHistory));
    }
}

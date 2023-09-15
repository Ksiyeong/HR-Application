package com.echonrich.hr.domain.job.service;

import com.echonrich.hr.domain.job.dto.JobHistoryParams;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobHistoryService {
    Page<JobHistoryResponseDto> findJobHistories(JobHistoryParams params, Pageable pageable);
}

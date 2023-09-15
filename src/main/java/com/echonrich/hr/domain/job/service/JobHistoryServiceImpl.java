package com.echonrich.hr.domain.job.service;

import com.echonrich.hr.domain.job.dto.JobHistoryParams;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.repository.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {
    private final JobHistoryRepository jobHistoryRepository;

    @Override
    public Page<JobHistoryResponseDto> findJobHistories(JobHistoryParams params, Pageable pageable) {
        return jobHistoryRepository.findJobHistories(params, pageable);
    }
}

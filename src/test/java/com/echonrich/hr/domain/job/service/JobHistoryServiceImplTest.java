package com.echonrich.hr.domain.job.service;

import com.echonrich.hr.domain.job.dto.JobHistoryParams;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.factory.JobHistoryResponseDtoFactory;
import com.echonrich.hr.domain.job.repository.JobHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class JobHistoryServiceImplTest {
    @InjectMocks
    private JobHistoryServiceImpl jobHistoryService;
    @Mock
    private JobHistoryRepository jobHistoryRepository;

    @Test
    @DisplayName("findJobHistories - 성공")
    void findJobHistories() {
        // given
        JobHistoryParams params = new JobHistoryParams(101L, null, null, null, null);
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<JobHistoryResponseDto> expected = JobHistoryResponseDtoFactory.createJobHistoryResponseDtos(pageRequest);

        given(jobHistoryRepository.findJobHistories(any(), any())).willReturn(expected);

        // when
        Page<JobHistoryResponseDto> actual = jobHistoryService.findJobHistories(params, pageRequest);

        // then
        verify(jobHistoryRepository, times(1)).findJobHistories(any(), any());

        assertEquals(pageRequest.getPageNumber(), actual.getNumber());
        assertEquals(pageRequest.getPageSize(), actual.getSize());
        assertSame(expected, actual);
    }
}
package com.echonrich.hr.domain.job.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class JobDto {
    @NoArgsConstructor
    @Getter
    public static class SimpleResponse {
        private String jobId;
        private String jobTitle;

        @Builder
        @QueryProjection
        public SimpleResponse(String jobId,
                              String jobTitle) {
            this.jobId = jobId;
            this.jobTitle = jobTitle;
        }
    }
}

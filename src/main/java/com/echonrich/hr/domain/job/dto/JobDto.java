package com.echonrich.hr.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class JobDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class SimpleResponse {
        private String jobId;
        private String jobTitle;
    }
}

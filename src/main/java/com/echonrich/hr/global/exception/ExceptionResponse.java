package com.echonrich.hr.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ExceptionResponse {
    private String title;
    private Integer status;
    private String detail;
    private LocalDateTime timestamp;
}
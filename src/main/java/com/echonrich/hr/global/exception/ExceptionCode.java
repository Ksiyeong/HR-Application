package com.echonrich.hr.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 데이터를 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}

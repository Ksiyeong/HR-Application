package com.echonrich.hr.infrastructure.arirang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ArirangResponseDto {
    private Integer resultCode;
    private String resultMsg;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
    private List<ArirangItemsResponseDto> items;
}

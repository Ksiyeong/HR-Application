package com.echonrich.hr.infrastructure.arirang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ArirangItemsResponseDto {
    private String title;
    private String content;
    private String news_url;
    private String thum_url;
    private String broadcast_date;
}

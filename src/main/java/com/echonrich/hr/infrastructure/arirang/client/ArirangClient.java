package com.echonrich.hr.infrastructure.arirang.client;

import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import org.springframework.data.domain.Page;

public interface ArirangClient {
    Page<ArirangItemsResponseDto> findArirangNews(int page, int size);
}

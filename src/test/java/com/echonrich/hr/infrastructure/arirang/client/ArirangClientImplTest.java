package com.echonrich.hr.infrastructure.arirang.client;

import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ArirangClientImplTest {
    @Autowired
    private ArirangClientImpl arirangClient;

    @Test
    void findArirangNews() {
        // given
        int page = 1;
        int size = 10;

        // when
        Page<ArirangItemsResponseDto> arirangNews = arirangClient.findArirangNews(page, size);

        // then
        assertEquals(page - 1, arirangNews.getNumber());
        assertEquals(size, arirangNews.getSize());
    }
}
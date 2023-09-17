package com.echonrich.hr.infrastructure.arirang.client;

import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import com.echonrich.hr.infrastructure.arirang.dto.ArirangResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ArirangClientImpl implements ArirangClient {
    private final WebClient webClient;
    @Value("${infrastructure.arirang.service-key}")
    private String ENCODED_ARIRANG_SERVICE_KEY;

    @Override
    public Page<ArirangItemsResponseDto> findArirangNews(int page, int size) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://apis.data.go.kr/B551024/openArirangNewsApi/news")
                .queryParam("serviceKey", ENCODED_ARIRANG_SERVICE_KEY)
                .queryParam("pageNo", page)
                .queryParam("numOfRows", size)
                .build(true)
                .toUri();

        ArirangResponseDto arirangResponseDto = webClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ArirangResponseDto.class)
                .block();

        return new PageImpl<>(arirangResponseDto.getItems(), PageRequest.of(page - 1, size), arirangResponseDto.getTotalCount());
    }
}

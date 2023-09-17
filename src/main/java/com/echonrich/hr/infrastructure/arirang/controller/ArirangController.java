package com.echonrich.hr.infrastructure.arirang.controller;

import com.echonrich.hr.global.response.PageResponse;
import com.echonrich.hr.infrastructure.arirang.client.ArirangClient;
import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/arirangs")
public class ArirangController {
    private final ArirangClient arirangClient;

    @GetMapping("/news")
    public ResponseEntity getArirangNews(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Page<ArirangItemsResponseDto> arirangNews = arirangClient.findArirangNews(page, size);
        return ResponseEntity.ok(new PageResponse<>(arirangNews));
    }

}

package com.echonrich.hr.infrastructure.arirang.controller;

import com.echonrich.hr.infrastructure.arirang.client.ArirangClient;
import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArirangController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ArirangControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArirangClient arirangClient;

    @Test
    @DisplayName("getArirangNews - 성공")
    void getArirangNews() throws Exception {
        // given
        int page = 2;
        int size = 10;
        long totalElements = 23L;
        int totalPages = (int) (totalElements / size + (totalElements % size == 0 ? 0 : 1));

        List<ArirangItemsResponseDto> arirangItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arirangItems.add(new ArirangItemsResponseDto());
        }
        Page<ArirangItemsResponseDto> arirangItemsResponseDtos = new PageImpl<>(arirangItems, PageRequest.of(page - 1, size), totalElements);

        given(arirangClient.findArirangNews(anyInt(), anyInt())).willReturn(arirangItemsResponseDtos);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/arirangs/news")
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(totalElements))
                .andExpect(jsonPath("$.totalPages").value(totalPages))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.data", hasSize(size)));
    }

    @Test
    @DisplayName("getArirangNews - 성공 : page 값이 주어지지 않았을 경우")
    void getArirangNews_page_not_given() throws Exception {
        // given
        int expectedPage = 1;
        int size = 10;
        long totalElements = 23L;
        int totalPages = (int) (totalElements / size + (totalElements % size == 0 ? 0 : 1));

        List<ArirangItemsResponseDto> arirangItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arirangItems.add(new ArirangItemsResponseDto());
        }
        Page<ArirangItemsResponseDto> arirangItemsResponseDtos = new PageImpl<>(arirangItems, PageRequest.of(expectedPage - 1, size), totalElements);

        given(arirangClient.findArirangNews(anyInt(), anyInt())).willReturn(arirangItemsResponseDtos);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/arirangs/news")
                        .queryParam("size", String.valueOf(size))
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(totalElements))
                .andExpect(jsonPath("$.totalPages").value(totalPages))
                .andExpect(jsonPath("$.page").value(expectedPage))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.data", hasSize(size)));
    }

    @Test
    @DisplayName("getArirangNews - 성공 : size 값이 주어지지 않았을 경우")
    void getArirangNews_size_not_given() throws Exception {
        // given
        int page = 1;
        int expectedSize = 10;
        long totalElements = 23L;
        int totalPages = (int) (totalElements / expectedSize + (totalElements % expectedSize == 0 ? 0 : 1));

        List<ArirangItemsResponseDto> arirangItems = new ArrayList<>();
        for (int i = 0; i < expectedSize; i++) {
            arirangItems.add(new ArirangItemsResponseDto());
        }
        Page<ArirangItemsResponseDto> arirangItemsResponseDtos = new PageImpl<>(arirangItems, PageRequest.of(page - 1, expectedSize), totalElements);

        given(arirangClient.findArirangNews(anyInt(), anyInt())).willReturn(arirangItemsResponseDtos);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/arirangs/news")
                        .queryParam("page", String.valueOf(page))
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(totalElements))
                .andExpect(jsonPath("$.totalPages").value(totalPages))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.size").value(expectedSize))
                .andExpect(jsonPath("$.data", hasSize(expectedSize)));
    }
}
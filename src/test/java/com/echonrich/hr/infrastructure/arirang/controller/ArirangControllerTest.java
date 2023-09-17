package com.echonrich.hr.infrastructure.arirang.controller;

import com.echonrich.hr.infrastructure.arirang.client.ArirangClient;
import com.echonrich.hr.infrastructure.arirang.dto.ArirangItemsResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
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
        int page = 1;
        int size = 3;
        long totalElements = 23L;
        int totalPages = (int) (totalElements / size + (totalElements % size == 0 ? 0 : 1));

        List<ArirangItemsResponseDto> arirangItems = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            arirangItems.add(ArirangItemsResponseDto.builder()
                    .title("Sameple Title " + i)
                    .content("This is sample content " + i)
                    .news_url("https://this.is.sample.url" + i)
                    .thum_url("https://this.is.sample.thumbnail.url" + i)
                    .broadcast_date("2023-09-1" + i + " 21:00:00")
                    .build());
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
                .andExpect(jsonPath("$.data", hasSize(size)))
                .andDo(document(
                        "getArirangNews",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("요청할 페이지").optional()
                                        .attributes(key("default").value("1"))
                                        .attributes(key("constraints").value("1이상"))
                                        .attributes(key("type").value("Number")),
                                parameterWithName("size").description("페이지 당 데이터 갯수").optional()
                                        .attributes(key("constraints").value("1이상"))
                                        .attributes(key("default").value("10"))
                                        .attributes(key("type").value("Number"))),
                        responseFields(
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 당 데이터 갯수"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[*].title").type(JsonFieldType.STRING).description("뉴스 제목"),
                                fieldWithPath("data[*].content").type(JsonFieldType.STRING).description("뉴스 본문"),
                                fieldWithPath("data[*].news_url").type(JsonFieldType.STRING).description("뉴스 링크"),
                                fieldWithPath("data[*].thum_url").type(JsonFieldType.STRING).description("썸네일 링크"),
                                fieldWithPath("data[*].broadcast_date").type(JsonFieldType.STRING).description("보도 날짜")
                        )
                ));
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
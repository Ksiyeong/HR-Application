package com.echonrich.hr.global.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageResponse<T> {
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<T> data;

    public PageResponse(Page<T> data) {
        this.totalElements = data.getTotalElements();
        this.totalPages = data.getTotalPages();
        this.page = data.getNumber() + 1;
        this.size = data.getSize();
        this.data = data.getContent();
    }
}

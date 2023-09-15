package com.echonrich.hr.global.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public class PageParams {
    @Min(0)
    private int page;
    @Positive
    private int size;
    private Sort.Direction direction;
    private String sort;

    public PageParams(Integer page,
                      Integer size,
                      Sort.Direction direction,
                      String sort) {
        this.page = page == null ? 0 : page - 1;
        this.size = size == null ? 10 : size;
        this.direction = direction == null ? Sort.Direction.ASC : direction;
        this.sort = sort == null ? "default" : sort;
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(this.page, this.size, this.direction, this.sort);
    }
}

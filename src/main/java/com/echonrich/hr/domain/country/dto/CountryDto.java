package com.echonrich.hr.domain.country.dto;

import com.echonrich.hr.domain.region.dto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CountryDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private String countryId;
        private String countryName;
        private RegionDto.Response region;
    }
}

package com.echonrich.hr.domain.location.dto;

import com.echonrich.hr.domain.country.dto.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LocationDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long locationId;
        private String streetAddress;
        private String postalCode;
        private String city;
        private String stateProvince;
        private CountryDto.Response country;
    }
}

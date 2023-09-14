package com.echonrich.hr.domain.country.entity;

import com.echonrich.hr.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "countries")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    @Column(length = 2, columnDefinition = "CHAR(2) NOT NULL")
    private String countryId;

    @Column(length = 40)
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    private Region region;
}

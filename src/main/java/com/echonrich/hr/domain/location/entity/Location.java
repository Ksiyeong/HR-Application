package com.echonrich.hr.domain.location.entity;

import com.echonrich.hr.domain.country.entity.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "locations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private Long locationId;

    @Column(length = 40)
    private String streetAddress;

    @Column(length = 12)
    private String postalCode;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(length = 25)
    private String stateProvince;

    @ManyToOne
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;
}

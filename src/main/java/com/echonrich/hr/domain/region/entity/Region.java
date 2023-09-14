package com.echonrich.hr.domain.region.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "regions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private Long regionId;

    @Column(length = 25)
    private String regionName;
}

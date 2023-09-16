package com.echonrich.hr.domain.department.entity;

import com.echonrich.hr.domain.employee.entity.Employee;
import com.echonrich.hr.domain.location.entity.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "departments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private Long departmentId;

    @Column(length = 30, nullable = false)
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "managerId")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

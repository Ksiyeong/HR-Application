package com.echonrich.hr.domain.department.repository;

import com.echonrich.hr.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

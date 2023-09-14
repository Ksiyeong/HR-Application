package com.echonrich.hr.domain.employee.repository;

import com.echonrich.hr.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

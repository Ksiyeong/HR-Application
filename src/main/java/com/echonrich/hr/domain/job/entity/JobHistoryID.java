package com.echonrich.hr.domain.job.entity;

import com.echonrich.hr.domain.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobHistoryID {
    private Employee employee;
    private Date startDate;
}

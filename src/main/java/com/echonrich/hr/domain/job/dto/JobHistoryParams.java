package com.echonrich.hr.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class JobHistoryParams {
    @Range(min = 1L, max = 4294967295L)
    private Long employee_id;
    private Date start_date;
    private Date end_date;
    private String job_id;
    @Range(min = 1L, max = 4294967295L)
    private Long department_id;
}

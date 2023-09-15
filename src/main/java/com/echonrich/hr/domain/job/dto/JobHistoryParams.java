package com.echonrich.hr.domain.job.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class JobHistoryParams {
    @Min(1)
    private Long employee_id;
    private Date start_date;
    private Date end_date;
    private String job_id;
    private Long department_id;
}

package com.echonrich.hr.domain.job.repository;

import com.echonrich.hr.domain.job.entity.JobHistory;
import com.echonrich.hr.domain.job.entity.JobHistoryID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryID>, JobHistoryRepositoryCustom {
    Page<JobHistory> findAllByEmployee_EmployeeId(long employeeId, Pageable pageable);
}

package com.echonrich.hr.domain.job.repository;

import com.echonrich.hr.domain.department.dto.QDepartmentDto_SimpleResponse;
import com.echonrich.hr.domain.employee.dto.QEmployeeDto_SimpleResponse;
import com.echonrich.hr.domain.job.dto.JobHistoryParams;
import com.echonrich.hr.domain.job.dto.JobHistoryResponseDto;
import com.echonrich.hr.domain.job.dto.QJobDto_SimpleResponse;
import com.echonrich.hr.domain.job.dto.QJobHistoryResponseDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.echonrich.hr.domain.job.entity.QJobHistory.jobHistory;

@RequiredArgsConstructor
public class JobHistoryRepositoryCustomImpl implements JobHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<JobHistoryResponseDto> findJobHistories(JobHistoryParams params, Pageable pageable) {
        // 쿼리
        JPAQuery<JobHistoryResponseDto> query = queryFactory
                .select(new QJobHistoryResponseDto(
                        new QEmployeeDto_SimpleResponse(
                                jobHistory.employee.employeeId,
                                jobHistory.employee.firstName,
                                jobHistory.employee.lastName),
                        jobHistory.startDate,
                        jobHistory.endDate,
                        new QJobDto_SimpleResponse(jobHistory.job.jobId,
                                jobHistory.job.jobTitle),
                        new QDepartmentDto_SimpleResponse(
                                jobHistory.department.departmentId,
                                jobHistory.department.departmentName)))
                .from(jobHistory)
                .where(
                        params.getEmployee_id() == null ? null : jobHistory.employee.employeeId.eq(params.getEmployee_id()),
                        params.getStart_date() == null ? null : jobHistory.startDate.goe(params.getStart_date()),
                        params.getEnd_date() == null ? null : jobHistory.endDate.loe(params.getEnd_date()),
                        params.getJob_id() == null ? null : jobHistory.job.jobId.eq(params.getJob_id()),
                        params.getDepartment_id() == null ? null : jobHistory.department.departmentId.eq(params.getDepartment_id())
                );

        // 정렬 조건, 페이지 및 결과
        List<JobHistoryResponseDto> result = query
                .orderBy(pageableToOrderSpecifier(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(result, pageable, query::fetchCount);
    }

    // 정렬 조건
    private OrderSpecifier pageableToOrderSpecifier(Pageable pageable) {
        Sort.Order sortOrder = pageable.getSort().get().collect(Collectors.toList()).get(0);

        Order order = sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;
        Expression sort = propertyToSortExpression(sortOrder.getProperty());

        return new OrderSpecifier(order, sort);
    }

    private Expression propertyToSortExpression(String property) {
        return switch (property) {
            case "start_date" -> jobHistory.startDate;
            case "end_date" -> jobHistory.endDate;
            case "job_id" -> jobHistory.job.jobId;
            case "department_id" -> jobHistory.department.departmentId;
            default -> jobHistory.employee.employeeId; // employee_id 및 기타 외에 다른값 들어오면 기본값으로 검색
        };
    }
}

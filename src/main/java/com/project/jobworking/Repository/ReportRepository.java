package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByReportName(String reportName);

    Report findTopByJobIdOrderByCreatedDateDesc(Long jobId);

    List<Report> findAllByJobName(String jobName);

    List<Report> findAllByJobId(Long jobId);

    List<Report> findByCreatedByContainingIgnoreCase(String createdBy);

    List<Report> findByJobNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(String jobName, String createdBy);
}

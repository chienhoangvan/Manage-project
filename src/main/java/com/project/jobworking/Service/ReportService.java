package com.project.jobworking.Service;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.Report;

import java.util.Date;
import java.util.List;

public interface ReportService {
    Report save(Report report);

    List<Report> findAll();

    Report findById(Long reportId);

    Report findByName(String reportName);

    List<Report> findAllByJobName(String jobName);

    List<Report> findAllByJobId(Long jobId);

    List<Report> getByCreater(String createdBy);

    List<Report> getByJobNameAndCreater(String jobName, String createdBy);

    List<Report> searchReports(String jobName, String createBy);

    void deleteById(Long reportId);
}

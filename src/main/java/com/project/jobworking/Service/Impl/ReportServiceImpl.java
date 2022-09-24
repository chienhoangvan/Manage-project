package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.Report;
import com.project.jobworking.Repository.ReportRepository;
import com.project.jobworking.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report save(Report report)
    {
        reportRepository.save(report);
        return report;
    }

    @Override
    public List<Report> findAll()
    {
        return (List<Report>) reportRepository.findAll();
    }

    @Override
    public Report findById(Long jobId)
    {
        Report report = reportRepository.findById(jobId).get();
        return report;
    }

    @Override
    public Report findByName(String reportName)
    {
        Report report = reportRepository.findByReportName(reportName);
        return report;
    }

    @Override
    public List<Report> findAllByJobName(String jobName){
        return reportRepository.findAllByJobName(jobName);
    }

    @Override
    public List<Report> findAllByJobId(Long jobId) {
        return reportRepository.findAllByJobId(jobId);
    }

    @Override
    public List<Report> getByCreater(String createdBy){
        return reportRepository.findByCreatedByContainingIgnoreCase(createdBy);
    }
    @Override
    public List<Report> getByJobNameAndCreater(String jobName, String createBy){
        return reportRepository.findByJobNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(jobName, createBy);
    }


    @Override
    public List<Report> searchReports(String jobName, String createdBy){

        List<Report> reportList = new ArrayList<Report>();

        if (jobName != null && createdBy == null) {
            reportList = findAllByJobName(jobName);
        } else if (jobName == null && createdBy != null) {
            reportList = getByCreater(createdBy);
        } else if (jobName != null && createdBy != null) {
            reportList = getByJobNameAndCreater(jobName, createdBy);
        }

        return reportList;
    }

    @Override
    public void deleteById(Long reportId) {
        reportRepository.deleteById(reportId);
    }

}

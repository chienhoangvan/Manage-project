package com.project.jobworking.Service;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;

import java.util.Date;
import java.util.List;

public interface JobService {
    Job save(Job job);

    List<Job> findAll();

    Job findById(Long jobId);

    Job findByName(String jobName);

    List<Job> findAllByProject(String projectName);

    List<Job> findAllByProject(Long projectId);

    List<Job> getByCreater(String createdBy);

    List<Job> getByProjectNameAndCreater(String projectName, String createdBy);

    List<Job> searchJobs(String projectName, String createdBy);

    void deleteById(Long jobId);

    void updateJob(Long id, String name,Date startDate, Date endDate, String description, String status);

    void assignJob(Long projectId, String MSSV);
}

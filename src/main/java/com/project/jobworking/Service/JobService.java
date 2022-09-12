package com.project.jobworking.Service;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;

import java.util.List;

public interface JobService {
    Job save(Job job);

    List<Job> findAll();

    Job findById(Long jobId);

    Job findByName(String jobName);

    List<Job> findAllByProject(String projectName);

    List<Job> findAllByProject(Long projectId);

    List<Job> getByCreater(String createBy);

    List<Job> getByProjectNameAndCreater(String projectName, String createBy);

    List<Job> searchJobs(String projectName, String createBy);

    void deleteById(Long jobId);

}

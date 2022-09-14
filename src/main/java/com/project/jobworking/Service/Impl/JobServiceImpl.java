package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Repository.JobRepository;
import com.project.jobworking.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job save(Job job)
    {
        jobRepository.save(job);
        return job;
    }

    @Override
    public List<Job> findAll()
    {
        return (List<Job>) jobRepository.findAll();
    }

    @Override
    public Job findById(Long jobId)
    {
        Job job = jobRepository.findById(jobId).get();
        return job;
    }

    @Override
    public Job findByName(String jobName)
    {
        Job job = jobRepository.findByNameEqualsIgnoreCase(jobName);
        return job;
    }

    @Override
    public List<Job> findAllByProject(String projectName){
        return jobRepository.findByProjectNameContainingIgnoreCase(projectName);
    }

    @Override
    public List<Job> findAllByProject(Long projectId) {
        return jobRepository.findByProject(projectId);
    }

    @Override
    public List<Job> getByCreater(String createdBy){
        return jobRepository.findByCreatedByContainingIgnoreCase(createdBy);
    }

    @Override
    public List<Job> getByProjectNameAndCreater(String projectName, String createdBy){
        return jobRepository.findByProjectNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(projectName, createdBy);
    }

    @Override
    public List<Job> searchJobs(String projectName, String createdBy){

        List<Job> jobList = new ArrayList<Job>();

        if (projectName != null && createdBy == null) {
            jobList = findAllByProject(projectName);
        } else if (projectName == null && createdBy != null) {
            jobList = getByCreater(createdBy);
        } else if (projectName != null && createdBy != null) {
            jobList = getByProjectNameAndCreater(projectName, createdBy);
        }

        return jobList;
    }

    @Override
    public void deleteById(Long jobId) {
        jobRepository.deleteById(jobId);
    }

}

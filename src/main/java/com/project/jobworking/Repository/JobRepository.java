package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    Job findByNameEqualsIgnoreCase(String jobName);

    void deleteByName(String jobName);
    List<Job> findByProject(Long projectId);

    List<Job> findByProjectNameContainingIgnoreCase(String projectName);
    List<Job> findByCreatedByContainingIgnoreCase(String createdBy);
    List<Job> findByProjectNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(String projectName, String createdBy);

    List<Job> findAllByUser(User user);

    List<Job> getByProjectNameAndUser(String projectName, User user);
}

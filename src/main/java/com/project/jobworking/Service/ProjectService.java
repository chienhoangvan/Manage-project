package com.project.jobworking.Service;

import com.project.jobworking.Entity.Project;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    List<Project> findAll();

    Project findById(Long projectId);

    List<Project> getByProjectName(String projectName);

    List<Project> getByCreater(String createBy);

    List<Project> getByProjectNameAndCreater(String projectName, String createBy);

    List<Project> searchProjects(String projectName, String createBy);

    void deleteById(Long projectId);

    boolean checkAvailabilityById(Long projectId);
}

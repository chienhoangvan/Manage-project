package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Repository.ProjectRepository;
import com.project.jobworking.Repository.UserRepository;
import com.project.jobworking.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Project save(Project project)
    {
        projectRepository.save(project);
        return project;
    }

    @Override
    public List<Project> findAll()
    {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Project findById(Long projectId)
    {
        Project project = projectRepository.findById(projectId).get();
        return project;
    }

    @Override
    public List<Project> getByProjectName(String projectName){
        return projectRepository.findByNameContainingIgnoreCase(projectName);
    }

    @Override
    public List<Project> getByCreater(String createBy){
        return projectRepository.findByCreatedByContainingIgnoreCase(createBy);
    }

    @Override
    public List<Project> getByProjectNameAndCreater(String title, String author){
        return projectRepository.findByNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(title, author);
    }

    @Override
    public List<Project> searchProjects(String projectName, String createBy){

        List<Project> searchedBooks = new ArrayList<Project>();

        if (projectName != null && createBy == null) {
            searchedBooks = getByProjectName(projectName);
        } else if (projectName == null && createBy != null) {
            searchedBooks = getByCreater(createBy);
        } else if (projectName != null && createBy != null) {
            searchedBooks = getByProjectNameAndCreater(projectName, createBy);
        }

        return searchedBooks;
    }

    @Override
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public boolean checkAvailabilityById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) return true;
        return false;
    }
}

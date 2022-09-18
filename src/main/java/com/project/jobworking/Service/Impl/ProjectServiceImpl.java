package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import com.project.jobworking.Repository.ProjectRepository;
import com.project.jobworking.Repository.UserRepository;
import com.project.jobworking.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
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
        project.setIsUsed(false);
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
    public List<Project> getByCreater(String createdBy){
        return projectRepository.findByCreatedByContainingIgnoreCase(createdBy);
    }

    @Override
    public List<Project> getByProjectNameAndCreater(String projectName, String createBy){
        return projectRepository.findByNameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(projectName, createBy);
    }

    @Override
    public List<Project> searchProjects(String projectName, String creatdeBy){

        List<Project> projectList = new ArrayList<Project>();

        if (projectName != null && creatdeBy == null) {
            projectList = getByProjectName(projectName);
        } else if (projectName == null && creatdeBy != null) {
            projectList = getByCreater(creatdeBy);
        } else if (projectName != null && creatdeBy != null) {
            projectList = getByProjectNameAndCreater(projectName, creatdeBy);
        }

        return projectList;
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

    @Override
    public void updateProject(Long id,String name,Date startDate, Date endDate, String description) {
        Project project = this.findById(id);
        project.setName(name);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setDescription(description);
        this.save(project);
    }

    @Override
    public void assignProject(Long projectId, String MSSV) {
        Optional<User> userOptional = userRepository.findByMSSVAndRole(MSSV, "student");
        if (!userOptional.isPresent()) return;
        Project project = this.findById(projectId);
        project.setIsUsed(true);
        projectRepository.save(project);

        User user = userOptional.get();
        user.setProject(project);
        userRepository.save(user);
    }
}

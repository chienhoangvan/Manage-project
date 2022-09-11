package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String projectName);

    void deleteByName(String projectName);
    List<Project> findByProject_nameContainingIgnoreCase(String name);
    List<Project> findByCreatedByContainingIgnoreCase(String createdBy);
    List<Project> findByProject_nameContainingIgnoreCaseAndCreatedByContainingIgnoreCase(String title, String author);


}

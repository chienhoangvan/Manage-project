package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Comment;
import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProject(Project project);

    List<Comment> findAllByJob(Job job);
}

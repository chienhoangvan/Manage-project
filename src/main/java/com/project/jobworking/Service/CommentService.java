package com.project.jobworking.Service;

import com.project.jobworking.Entity.Comment;
import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment createForProject(Long projectId, Comment comment);

    Comment createForJob(Long jobId, Comment comment);

    Comment save(Comment comment);

    List<Comment> findAllByProject(Project project);

    List<Comment> findAllByJob(Job job);

    void deleteById(Long commentId);

    void updateComment(Long commentId, String substance);
}

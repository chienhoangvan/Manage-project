package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.Comment;
import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Repository.CommentRepository;
import com.project.jobworking.Repository.JobRepository;
import com.project.jobworking.Repository.ProjectRepository;
import com.project.jobworking.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Comment createForProject(Long projectId, Comment comment) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (!projectOptional.isPresent()) return null;
        comment.setProject(projectOptional.get());
        return commentRepository.save(comment);
    }
    @Override
    public Comment createForJob(Long jobId, Comment comment) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (!jobOptional.isPresent()) return null;
        comment.setJob(jobOptional.get());
        return commentRepository.save(comment);
    }


    @Override
    public Comment save(Comment comment)
    {
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public List<Comment> findAllByProject(Project project) {
        List<Comment> commentList = commentRepository.findAllByProject(project);
        return commentList;
    }

    @Override
    public List<Comment> findAllByJob(Job job) {
        List<Comment> commentList = commentRepository.findAllByJob(job);
        return commentList;
    }

    @Override
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(Long commentId, String substance) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (!commentOptional.isPresent()) return;
        Comment comment = commentOptional.get();
        comment.setSubstance(substance);
        commentRepository.save(comment);
    }

}

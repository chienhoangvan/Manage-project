package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Result;
import com.project.jobworking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByUser(User user);

    void deleteById(Long resultId);

    List<Result> findAll();

    List<Result> findAllByProjectId(Long projectId);

}

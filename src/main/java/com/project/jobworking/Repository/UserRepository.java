package com.project.jobworking.Repository;

import com.project.jobworking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);

    Optional<User> findByUsername(String username);

    List<User> findByUsernameAndRole(String username, String role);
    Optional<User> findByMSSVAndRole(String MSSV, String role);

    List<User> findAllByRole(String role);

    List<User> findByProjectId(Long projectId);
}

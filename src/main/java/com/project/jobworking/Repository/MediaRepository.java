package com.project.jobworking.Repository;

import com.project.jobworking.Entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByCreatedByAndJobId(String createdBy, Long jobId);

    List<Media> findAllByJobId(Long jobId);

    void deleteById(Long mediaId);
}

package com.project.jobworking.Service.Impl;

import com.project.jobworking.Repository.MediaRepository;
import com.project.jobworking.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public void deleteById(Long jobId) {
        mediaRepository.deleteById(jobId);
    }
}

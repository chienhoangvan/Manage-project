package com.project.jobworking.Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Media;
import com.project.jobworking.Repository.JobRepository;
import com.project.jobworking.Repository.MediaRepository;
import com.project.jobworking.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/upload-single-file")
    public Media uploadSingleFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam("jobId") Long jobId,
                                  @RequestParam("projectId") Long projectId) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        Media fileUpload = new Media(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        if (Objects.nonNull(jobId)) {
            fileUpload.setJobId(jobId);
        }
        if (Objects.nonNull(projectId)){
            fileUpload.setProjectId(projectId);
        }
        mediaRepository.save(fileUpload);

        return fileUpload;
    }

    @PostMapping("/upload-multiple-files")
    public List<Media> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                           @RequestParam("jobId") Long jobId,
                                           @RequestParam("projectId") Long projectId) {
        return Arrays.stream(files).map(x -> this.uploadSingleFile(x, jobId, projectId))
                .collect(Collectors.toList());
    }

    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

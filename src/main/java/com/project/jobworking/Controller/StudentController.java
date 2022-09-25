package com.project.jobworking.Controller;

import com.project.jobworking.Entity.*;
import com.project.jobworking.Repository.JobRepository;
import com.project.jobworking.Repository.MediaRepository;
import com.project.jobworking.Repository.ResultRepository;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping
    public String employeeHomePage(Model model) {
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = userService.findById(currentUserId);
        model.addAttribute("currentUser", currentUser);

        return "student/student-home.html";
    }

    @GetMapping(value = "/projects/viewMyProject")
    public String viewMyProject(Model model) {
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User user = userService.findById(currentUserId);
        Project project = new Project();
        if (Objects.nonNull(user.getProject().getId())){
            project = projectService.findById(user.getProject().getId());

        } else project = null;
        Result result = resultRepository.findByUser(user).orElse(null);
        List<Comment> commentList = commentService.findAllByProject(project);
        if (Objects.nonNull(result)) {
            model.addAttribute("result", result);
        }
        model.addAttribute("project", project);
        model.addAttribute("comments", commentList);
        model.addAttribute("comment", new Comment());
        return "student/project/student-view-project.html";
    }

    @GetMapping(value = "/jobs/showOwnJobs")
    public String showOwnJobs(Model model,
                              @RequestParam(required = false) String jobName) {
        List<Job> jobs;
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User user = userService.findById(currentUserId);
        if ((jobName == null || jobName.isEmpty())) {
            jobs = jobRepository.findAllByUser(user);
        } else {
            jobs = jobRepository
                    .getByProjectNameAndUser(jobName,user);
        }
        model.addAttribute("jobs", jobs);
        return "student/job/student-show-own-jobs.html";
    }

    @GetMapping(value = "/jobs/{id}")
    public String viewJob(Model model, @PathVariable Long id) {
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User user = userService.findById(currentUserId);
        Job job = jobService.findById(id);
        List<Comment> commentList = commentService.findAllByJob(job);
        List<Media> mediaList = mediaRepository.findByCreatedByAndJobId(user.getUsername(), id);
        model.addAttribute("job", job);
        model.addAttribute("report", new Report());
        model.addAttribute("comments", commentList);
        model.addAttribute("mediaList", mediaList);
        model.addAttribute("comment", new Comment());
        return "student/job/view-job.html";
    }

    @PostMapping(value = "/comments/newCommentForProject")
    public String newCommentForProject(Model model, Comment comment, @RequestParam Long projectId) {
        commentService.createForProject(projectId, comment);
        return "redirect:/student/projects/viewMyProject";
    }

    @PostMapping(value = "/comments/newCommentForJob")
    public String newCommentForJob(Model model, Comment comment, @RequestParam Long jobId) {
        commentService.createForJob(jobId, comment);
        return "redirect:/student/jobs/" + jobId;
    }

    /*------------Report-------------*/

    @PostMapping(value = "/reports/save")
    public String saveReport(Report report, @RequestParam Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        report.setJob(job);
        reportService.save(report);
        if (Objects.nonNull(job)) {
            if (job.getEndDate().before(report.getCreatedDate())) {
                job.setStatus("Late Submit");
            } else job.setStatus("Done");
        }
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User user = userService.findById(currentUserId);
        List<Media> mediaList = mediaRepository.findByCreatedByAndJobId(user.getUsername(), jobId);
        for (Media media : mediaList) {
            media.setReportId(report.getId());
            mediaRepository.save(media);
        }
        return "redirect:/student/jobs/" + jobId;
    }


    /*------------Up load file ----------*/
    @PostMapping("/upload-single-file")
    public String uploadSingleFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam("jobId") Long jobId, Model model) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        Media fileUpload = new Media(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        if (Objects.nonNull(jobId)) {
            fileUpload.setJobId(jobId);
        }
        mediaRepository.save(fileUpload);

        return "redirect:/student/jobs/" + jobId;
    }

//    @PostMapping("/upload-multiple-files")
//    public List<Media> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
//                                           @RequestParam("jobId") Long jobId,
//                                           @RequestParam("projectId") Long projectId) {
//        return Arrays.stream(files).map(x -> this.uploadSingleFile(x, jobId, projectId))
//                .collect(Collectors.toList());
//    }

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

    @PostMapping(value = "/reports/deleteMedia")
    public String deleteMedia(@RequestParam("mediaId") Long mediaId,
                              @RequestParam("jobId") Long jobId) {
        mediaRepository.deleteById(mediaId);
        return "redirect:/student/jobs/" + jobId;
    }
}

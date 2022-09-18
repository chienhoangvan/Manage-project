package com.project.jobworking.Controller;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import com.project.jobworking.Repository.JobRepository;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.JobService;
import com.project.jobworking.Service.ProjectService;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

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
        User currentUser = userService.findById(currentUserId);
        Project project = new Project();
        if (Objects.nonNull(currentUser.getProject().getId())){
            project = projectService.findById(currentUser.getProject().getId());

        } else project = null;
        model.addAttribute("project", project);
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
        Job job = jobService.findById(id);
        model.addAttribute("job", job);
        return "student/job/view-job.html";
    }
}

package com.project.jobworking.Controller;

import com.project.jobworking.Entity.Job;
import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import com.project.jobworking.Repository.ProjectRepository;
import com.project.jobworking.Repository.UserRepository;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.JobService;
import com.project.jobworking.Service.ProjectService;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private CurrentUserFinder currentUserFinder;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String employeeHomePage(Model model) {
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = userService.findById(currentUserId);
        model.addAttribute("currentUser", currentUser);

        return "teacher/teacher-home.html";
    }

    /*--------------------Manage Project-----------------*/
    @GetMapping(value = "/projects")
    public String projects() {
        return "redirect:/teacher/projects/showProjects";
    }

    @GetMapping(value = "/projects/{id}")
    public String viewProject(Model model, @PathVariable Long id) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/project/view-project.html";
    }

    @GetMapping(value = "/projects/view/{id}")
    public String viewProjectPublic(Model model, @PathVariable Long id) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/project/view-project-public.html";
    }

    @GetMapping(value = "/projects/showProjects")
    public String showProjects(Model model,
                            @RequestParam(required = false) String projectName,
                            @RequestParam(required = false) String createdBy) {
        List<Project> projects;
        if ((projectName == null || projectName.isEmpty()) && (createdBy == null || createdBy.isEmpty())) {
            projects = projectService.findAll();
        } else {
            projects = projectService.searchProjects(projectName, createdBy);
        }
        model.addAttribute("projects", projects);
        return "teacher/project/teacher-show-projects.html";
    }

    @GetMapping(value = "/projects/showOwnProjects")
    public String showOwnProjects(Model model,
                               @RequestParam(required = false) String projectName) {
        List<Project> projects;
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = userService.findById(currentUserId);
        if ((projectName == null || projectName.isEmpty())) {
            projects = projectService.getByCreater(currentUser.getUsername());
        } else {
            projects = projectService
                    .getByProjectNameAndCreater(projectName, currentUser.getUsername());
        }
        model.addAttribute("projects", projects);
        return "teacher/project/teacher-show-own-projects.html";
    }

    @PostMapping(value = "/projects/update/{id}")
    public String updateProject(@PathVariable Long id, @RequestParam String name,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                @RequestParam String description) {
        projectService.updateProject(id,name,endDate,description);
        return "teacher/project/teacher-project-information-changed.html";
    }

    @GetMapping(value = "/projects/newProject")
    public String newProject(Model model) {
        model.addAttribute("project", new Project());
        return "teacher/project/teacher-new-project.html";
    }

    @PostMapping(value = "/projects/save")
    public String saveProject(Project project) {
        projectService.save(project);
        return "teacher/project/teacher-project-saved.html";
    }

    @GetMapping(value = "/projects/areYouSureToDeleteProject")
    public String areYouSureToDeleteProject(@RequestParam Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/project/teacher-delete-project.html";
    }

    @PostMapping(value = "/projects/deleteProject")
    public String deleteProject(@RequestParam Long id) {
        projectService.deleteById(id);
        return "teacher/project/teacher-project-deleted.html";
    }


    @GetMapping(value = "/projects/changeProjectInfo")
    public String changeProjectInfo(@RequestParam Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/project/teacher-change-project-info.html";
    }

    @GetMapping(value = "/students/showStudents/{projectId}")
    public String showUsers(Model model, @PathVariable Long projectId,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String MSSV) {
        List<User> users;
        users = userService.shownStudents(name, MSSV);
        Project project = projectService.findById(projectId);
        model.addAttribute("users", users);
        model.addAttribute("project", project);
        return "teacher/project/teacher-assign-project.html";
    }

    @PostMapping(value = "/projects/assign/{MSSV}")
    public String assignProject(@RequestParam Long projectId, @PathVariable String MSSV) {
        projectService.assignProject(projectId, MSSV);
        return "teacher/project/teacher-assigned.html";
    }

    /*-------------------Devide project to jobs-----------------*/

    @GetMapping(value = "/jobs/newJob")
    public String newJob(Model model,@RequestParam Long projectId ) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("job", new Job());
        return "teacher/job/teacher-new-job.html";
    }

    @PostMapping(value = "/jobs/save")
    public String saveJob(Job job, @RequestParam Long projectId) {
        Project project = projectService.findById(projectId);
        job.setProject(project);
        jobService.save(job);
        return "teacher/job/teacher-job-saved.html";
    }

    @GetMapping(value = "/jobs/showOwnJobs")
    public String showOwnJobs(Model model,
                                  @RequestParam(required = false) String jobName) {
        List<Job> jobs;
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = userService.findById(currentUserId);
        if ((jobName == null || jobName.isEmpty())) {
            jobs = jobService.getByCreater(currentUser.getUsername());
        } else {
            jobs = jobService
                    .getByProjectNameAndCreater(jobName,currentUser.getUsername());
        }
        model.addAttribute("jobs", jobs);
        return "teacher/job/teacher-show-own-jobs.html";
    }

    @GetMapping(value = "/jobs/{id}")
    public String viewJob(Model model, @PathVariable Long id) {
        Job job = jobService.findById(id);
        model.addAttribute("job", job);
        return "teacher/job/view-job.html";
    }

    @PostMapping(value = "/jobs/update/{id}")
    public String updateJob(@PathVariable Long id, @RequestParam String name,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                            @RequestParam String description, @RequestParam String status) {
        jobService.updateJob(id,name,startDate,endDate,description,status);
        return "teacher/job/teacher-job-information-changed.html";
    }

    @GetMapping(value = "/jobs/areYouSureToDeleteJob")
    public String areYouSureToDeleteJob(@RequestParam Long id, Model model) {
        Job job = jobService.findById(id);
        model.addAttribute("job", job);
        return "teacher/job/teacher-delete-job.html";
    }

    @PostMapping(value = "/jobs/deleteJob")
    public String deleteJob(@RequestParam Long id) {
        jobService.deleteById(id);
        return "teacher/job/teacher-job-deleted.html";
    }

    @GetMapping(value = "/students/showJobStudents/{jobId}")
    public String showStudents(Model model, @PathVariable Long jobId,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String MSSV) {
        List<User> users;
        users = userService.shownStudents(name, MSSV);
        Job job = jobService.findById(jobId);
        model.addAttribute("users", users);
        model.addAttribute("job", job);
        return "teacher/job/teacher-assign-job.html";
    }
    @PostMapping(value = "/jobs/assign/{MSSV}")
    public String assignJob(@RequestParam Long jobId, @PathVariable String MSSV) {
        jobService.assignJob(jobId, MSSV);
        return "teacher/job/teacher-assigned.html";
    }

}

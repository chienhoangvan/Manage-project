package com.project.jobworking.Controller;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.ProjectService;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public String employeeHomePage(Model model) {
        Long currentUserId = currentUserFinder.getCurrentUserId();
        User currentUser = userService.findById(currentUserId);
        model.addAttribute("currentUser", currentUser);

        return "teacher/teacher-home.html";
    }

    //    --------------------Manage Project-----------------
    @GetMapping(value = "/projects")
    public String projects() {
        return "redirect:/teacher/projects/showProjects";
    }

    @GetMapping(value = "/projects/{id}")
    public String viewProject(Model model, @PathVariable Long projectId) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        return "teacher/view-project.html";
    }

    @GetMapping(value = "/projects/showProjects")
    public String showProjects(Model model,
                            @RequestParam(required = false) String projectName,
                            @RequestParam(required = false) String createBy) {
        List<Project> projects;
        if ((projectName == null || projectName.isEmpty()) && (createBy == null || createBy.isEmpty())) {
            projects = projectService.findAll();
        } else {
            projects = projectService.searchProjects(projectName, createBy);
        }
        model.addAttribute("projects", projects);
        return "employee/employee-show-books.html";
    }

    @PostMapping(value = "/projects/update/{projectId}")
    public String updateProject(@PathVariable Long projectId, @RequestParam String projectName,
                                @RequestParam Date endDate, @RequestParam String description,
                                @RequestParam Boolean isUsed) {
        Project project = projectService.findById(projectId);
        project.setName(projectName);
        project.setEndDate(endDate);
        project.setDescription(description);
        project.setIsUsed(isUsed);
        projectService.save(project);
        return "teacher/teacher-project-information-changed.html";
    }

    @GetMapping(value = "/projects/newProject")
    public String newProject(Model model) {
        model.addAttribute("project", new Project());
        return "teacher/teacher-new-project.html";
    }

    @PostMapping(value = "/projects/save")
    public String saveProject(Project project) {
        projectService.save(project);
        return "teacher/teacher-project-saved.html";
    }

    @GetMapping(value = "/projects/areYouSureToDeleteProject")
    public String areYouSureToDeleteProject(@RequestParam Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        model.addAttribute("deleteProject", project);
        return "teacher/teacher-delete-project.html";
    }

    @PostMapping(value = "/projects/deleteProject")
    public String deleteProject(@RequestParam Long projectId) {
        projectService.deleteById(projectId);
        return "redirect:/teacher/projects/projectDeleted";
    }

    @GetMapping(value = "/projects/projectDeleted")
    public String projectDeleted() {
        return "teacher/teacher-project-deleted.html";
    }

    @GetMapping(value = "/projects/changeProjectInfo")
    public String changeProjectInfo(@RequestParam Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        return "teacher/teacher-change-project-info.html";
    }

    @PutMapping(value = "/projects/saveProjectChange")
    public String updateProjectInfo(Project project) {
        return "teacher/teacher-project-information-changed.html";
    }

}

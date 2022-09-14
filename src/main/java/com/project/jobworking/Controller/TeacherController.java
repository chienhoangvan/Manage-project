package com.project.jobworking.Controller;

import com.project.jobworking.Entity.Project;
import com.project.jobworking.Entity.User;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.ProjectService;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    /*--------------------Manage Project-----------------*/
    @GetMapping(value = "/projects")
    public String projects() {
        return "redirect:/teacher/projects/showProjects";
    }

    @GetMapping(value = "/projects/{id}")
    public String viewProject(Model model, @PathVariable Long id) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/view-project.html";
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
        return "teacher/teacher-show-projects.html";
    }

    @PostMapping(value = "/projects/update/{id}")
    public String updateProject(@PathVariable Long id, @RequestParam String name,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                @RequestParam String description) {
        Project project = projectService.findById(id);
        project.setName(name);
        project.setEndDate(endDate);
        project.setDescription(description);
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
    public String areYouSureToDeleteProject(@RequestParam Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/teacher-delete-project.html";
    }

    @PostMapping(value = "/projects/deleteProject")
    public String deleteProject(@RequestParam Long id) {
        projectService.deleteById(id);
        return "redirect:/teacher/projects/projectDeleted";
    }

    @GetMapping(value = "/projects/projectDeleted")
    public String projectDeleted() {
        return "teacher/teacher-project-deleted.html";
    }

    @GetMapping(value = "/projects/changeProjectInfo")
    public String changeProjectInfo(@RequestParam Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "teacher/teacher-change-project-info.html";
    }


    /*-------------------Devide project to jobs-----------------*/

}

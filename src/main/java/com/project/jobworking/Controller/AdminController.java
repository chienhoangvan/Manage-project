package com.project.jobworking.Controller;

import com.project.jobworking.Entity.User;
import com.project.jobworking.Security.CurrentUserFinder;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CurrentUserFinder currentUserFinder;

    @Autowired
    private UserService userService;

    @GetMapping
    public String userHome(Model model) {
        User currentUser = currentUserFinder.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "admin/admin-home.html";
    }

    /*-----------------------Manage User--------------*/
    @GetMapping(value = "/users/showusers")
    public String showUsers(Model model,
                            @RequestParam(required = false) String name,
                            @RequestParam(defaultValue = "0") Long user_id) {
        List<User> users;
        if (user_id != 0) {
            users = new ArrayList<User>();
            users.add(userService.findById(user_id));
        } else if (name != null && !name.isEmpty()) {
            users = userService.getByName(name);
        } else {
            users = userService.findAll();
        }
        model.addAttribute("users", users);
        return "admin/admin-show-users.html";
    }

    @GetMapping(value = "/users/showuserinfo/{user_id}")
    public String showUserInfo(@PathVariable Long user_id,
                               Model model) {
        User user = userService.findById(user_id);
        model.addAttribute("user", user);
        return "admin/admin-show-user-info.html";
    }

    @PostMapping(value = "/users/update/{user_id}")
    public String updateUser(@PathVariable Long user_id, @RequestParam String username,
                             @RequestParam String name, @RequestParam(name = "dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                             @RequestParam String address, @RequestParam String number) {
        User user = userService.findById(user_id);
        user.setUsername(username);
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        user.setNumber(number);
        userService.save(user);
        return "admin/admin-user-infor-changed.html";
    }

    @GetMapping(value = "/users/areyousuretodeleteuser")
    public String areYouSureToDeleteUser(@RequestParam Long deleteUserId, Model model) {
        User user = userService.findById(deleteUserId);
        model.addAttribute("deleteUser", user);
        return "admin/admin-delete-user.html";
    }

    @PostMapping(value = "/users/deleteuser")
    public String deleteUser(@RequestParam Long deleteUserId) {
        userService.deleteById(deleteUserId);
        return "admin/admin-user-deleted.html";
    }
}

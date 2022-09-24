package com.project.jobworking.Security;

import com.project.jobworking.Entity.User;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserFinder {
    @Autowired
    UserService userService;

    public Long getCurrentUserId() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=details.getUsername();

        User user= userService.getByUsername(username);
        return user.getUser_id();

    }

    public User getCurrentUser() {
        User currentUser= userService.findById(getCurrentUserId());
        return currentUser;
    }
}

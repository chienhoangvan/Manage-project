package com.project.jobworking.Service;

import com.project.jobworking.Entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User save(User user);

    void saveById(Long userId);

    User findById(Long id);

    List<User> findAll();

    List<User> getByName(String name);

    User getByUsername(String username);
    void deleteById(Long userId);

    List<User> shownStudents(String name, String MSSV);
}

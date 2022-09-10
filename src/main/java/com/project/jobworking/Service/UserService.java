package com.project.jobworking.Service;

import com.project.jobworking.Entity.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    public User save(User user);

    public void saveById(Long userId);

    public User findById(Long id);

    public List<User> findAll();

    public List<User> getByName(String name);

    public User getByUsername(String username);

    public void deleteById(Long userId);
}

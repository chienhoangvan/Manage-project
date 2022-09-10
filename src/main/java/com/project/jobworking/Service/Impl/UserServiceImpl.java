package com.project.jobworking.Service.Impl;

import com.project.jobworking.Entity.User;
import com.project.jobworking.Repository.UserRepository;
import com.project.jobworking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveById(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public List<User> findAll(){
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<User> getByName(String name){
        List<User> users = new ArrayList<User>();
        users = userRepository.findByNameContainingIgnoreCase(name);
        return users;
    }

    @Override
    public User getByUsername(String username) {
        User user= userRepository.findByUsername(username).get();
        return user;
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}

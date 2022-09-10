package com.project.jobworking;

import com.project.jobworking.Entity.User;
import com.project.jobworking.Repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@SpringBootApplication
public class JobWorkingApplication {

    @Autowired
    BCryptPasswordEncoder pwEncoder;

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(JobWorkingApplication.class, args);
    }

    @Bean
    @Transactional
    InitializingBean initialCode() {
        return () -> {
            long count = userRepository.count();
            if (count == 0L) {
                User user = new User();
                user.setName("Admin");
                user.setUsername("admin");
                user.setPassword(pwEncoder.encode("123456"));
                user.setRole("admin");
                userRepository.save(user);
            }
        };
    }

}

package com.example.dodo.Services;

import com.example.dodo.Entity.User;
import com.example.dodo.Repositories.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(@NotBlank(message="UserEmail is required") String useremail) {
        return userRepository.existsByuseremail(useremail);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String useremail) {
        return userRepository.findByuseremail(useremail).orElse(null);
    }
}

package com.msmata.challenge.services;

import com.msmata.challenge.entities.User;
import com.msmata.challenge.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> validateUser(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password);
    }
}

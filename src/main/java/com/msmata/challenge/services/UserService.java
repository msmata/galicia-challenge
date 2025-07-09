package com.msmata.challenge.services;

import com.msmata.challenge.entities.User;
import com.msmata.challenge.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> validateUser(String userId, String password) {
        logger.debug("Validando credenciales para userId={}", userId);

        Optional<User> userOpt = userRepository.findByUserIdAndPassword(userId, password);

        if (userOpt.isPresent()) {
            logger.info("Autenticación exitosa para userId={}", userId);
        } else {
            logger.warn("Autenticación fallida para userId={}", userId);
        }

        return userOpt;
    }
}

package com.ardecs.ctshop.service;

import com.ardecs.ctshop.persistence.entity.Role;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.getRoles().add(Role.USER);
        userRepository.save(user);
    }

    public boolean isUserExists(User user) {

        User userFromDB = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        return userFromDB != null;

    }
}

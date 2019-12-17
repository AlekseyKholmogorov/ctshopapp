package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Role;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDataBase = userRepository.findByUsername(user.getUsername());

        if (userFromDataBase != null) {
            return "registration";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.getRoles().add(Role.USER);
        userRepository.save(user);
        return "redirect:/login";
    }
}

package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Role;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import com.ardecs.ctshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/user";
    }

    @GetMapping("/showFormForAddUser")
    public String showFormForAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/userForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/userForm";
        }

        User userFromDataBase = userRepository.findByUsernameAndEmail(user.getUsername(), user.getEmail());

        if (userFromDataBase != null) {
            return "admin/userForm";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        if (user.getUsername().toLowerCase().equals("admin")) {
            user.getRoles().add(Role.ADMIN);
            user.getRoles().remove(Role.USER);
        } else {
            user.getRoles().add(Role.USER);
        }
        userRepository.save(user);

        return "redirect:/user";
    }

    @GetMapping("showFormForUpdateUser/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "admin/userForm";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("user/userInfo/{id}")
    public String showUserInfo(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "user/userInfo";
    }

    @GetMapping("addProductToOrder/{id}")
    public String addProductToOrder(@PathVariable("id") Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);
        userService.buyProduct(user, id);
        return "redirect:/index";
    }


}

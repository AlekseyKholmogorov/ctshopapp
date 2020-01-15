package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import com.ardecs.ctshop.service.RegistrationService;
import com.ardecs.ctshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RegistrationService registrationService;


    public UserController(UserRepository userRepository, UserService userService, RegistrationService registrationService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("user")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/user";
    }

    @GetMapping("showFormForAddUser")
    public String showFormForAdd(Model model) {
        model.addAttribute("user", new User());
        return "admin/userForm";
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        //Нельзя убирать или менять эту проверку, иначу будет не возможно обновить существующего пользователя.
        // Эта проверка специально стоит перед проверкой существования пользователя.
        if (user.getId() != null && !bindingResult.hasErrors()) {
            registrationService.registration(user);
            return "redirect:/user";
        }

        if (bindingResult.hasErrors() || registrationService.isUserExists(user)) {
            return "admin/userForm";
        }

        registrationService.registration(user);
        return "redirect:/user";
    }

    @GetMapping("showFormForUpdateUser/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("user", user);
        return "admin/userForm";
    }
    @GetMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("user/userInfo/{id}")
    public String showUserInfo(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("user", user);
        return "user/userInfo";
    }

    @GetMapping("addProductToOrder/{id}")
    public String addProductToOrder(@PathVariable("id") Integer id, Principal principal) {

        if (principal == null) {
            return "redirect:/index";
        }

        User user = userRepository.findByUsername(principal.getName());
        return userService.buyProduct(user, id);
    }


}

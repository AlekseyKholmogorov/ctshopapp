package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors() | registrationService.isRegistrationValid(user)) {
            return "registration";
        }
        registrationService.registration(user);
        return "redirect:/login";
    }
}

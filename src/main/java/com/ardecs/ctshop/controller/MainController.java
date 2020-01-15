package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MainController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MainController(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/index", "", "/"})
    public String showMainPage(Model model, @RequestParam(defaultValue = "") String productName, Principal principal) {

        if (principal != null) {
            model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        }

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("products", productRepository.findByNameLike("%" + productName + "%"));
        return "index";
    }

}

package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showMainPage(Model model, @RequestParam(defaultValue = "") String productName) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User userFromDataBase = userRepository.findByUsername(name);
        if (userFromDataBase != null) {
            model.addAttribute("user", userFromDataBase);
        }
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("products", productRepository.findByNameLike("%" + productName + "%"));
        return "index";
    }

}

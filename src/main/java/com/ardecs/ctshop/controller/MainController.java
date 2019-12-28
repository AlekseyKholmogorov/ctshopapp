package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

        List<Category> categoryList = categoryRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User userFromDataBase = userRepository.findByUsername(name);
        if (userFromDataBase != null) {
            model.addAttribute("user", userFromDataBase);
        }
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", productRepository.findByNameLike("%" + productName + "%"));
        return "index";
    }

    @GetMapping("/products/{categoryName}")
    public String showProductsByCategory(@PathVariable("categoryName") String categoryName, Model model) {
        List<Product> productList = productRepository.findAllByCategory(categoryRepository.findByName(categoryName));
        model.addAttribute("products", productList);
        return "/products";
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }


}

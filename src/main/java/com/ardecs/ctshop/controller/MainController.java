package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MainController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/index", "", "/"})
    public String showMainPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User userFromDataBase = userRepository.findByUsername(name);
        if (userFromDataBase != null) {
            Optional<User> user = userRepository.findById(userFromDataBase.getId());
            model.addAttribute("user", user.get());
        }
        List<Product> list = productRepository.findAll();
        model.addAttribute("products", list);
        return "index";
    }

    @GetMapping("/coffee")
    public String showCoffee(Model model) {
        List<Product> list = productRepository.findProductsByCategoryCoffee();
        model.addAttribute("products", list);
        return "coffee";
    }

    @GetMapping("/tea")
    public String showTea(Model model) {
        List<Product> list = productRepository.findProductsByCategoryTea();
        model.addAttribute("products", list);
        return "tea";
    }

    @GetMapping("/accessories")
    public String showAccessories(Model model) {
        List<Product> list = productRepository.findProductsByCategoryAccessories();
        model.addAttribute("products", list);
        return "accessories";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }


}

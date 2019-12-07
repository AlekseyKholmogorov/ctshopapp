package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public MainController(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/index", "", "/"}, method = RequestMethod.GET)
    public String showMainPage(Model model) {
        Optional<User> user = userRepository.findById(1);
        List<Product> list = productRepository.findAll();
        model.addAttribute("products", list);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = "/coffee", method = RequestMethod.GET)
    public String showCoffee(Model model) {
        List<Product> list = productRepository.findProductsByCategoryCoffee();
        model.addAttribute("products", list);
        return "coffee";
    }

    @RequestMapping(value = "/tea", method = RequestMethod.GET)
    public String showTea(Model model) {
        List<Product> list = productRepository.findProductsByCategoryTea();
        model.addAttribute("products", list);
        return "tea";
    }

    @RequestMapping(value = "/accessories", method = RequestMethod.GET)
    public String showAccessories(Model model) {
        List<Product> list = productRepository.findProductsByCategoryAccessories();
        model.addAttribute("products", list);
        return "accessories";
    }
}

package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class OrderController {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderController(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("order/{id}")
    public String showOrder(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(1);
        Optional<Order> order = orderRepository.findById(id);
        Set<Product> products = order.get().getProducts();
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        return "order";
    }

    @GetMapping("deleteOrder/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        orderRepository.deleteById(id);
        return "redirect:/index";
    }
}


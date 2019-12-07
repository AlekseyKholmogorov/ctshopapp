package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public UserController(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/user")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/showFormForAddUser")
    public String showFormForAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("showFormForUpdateUser/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "userForm";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/userInfo/{id}")
    public String showUserInfo(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "userInfo";
    }

    @GetMapping("addProductToOrder/{id}")
    public String addProductToOrder(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(1);
        if (user.get().getOrders().size() == 0) {
            Order order = new Order(false, user.get());
            if (productRepository.findById(id).get().getQuantity() == 0) {
                return "noProductInStock";
            } else {
                order.getProducts().add(productRepository.findById(id).get());
                user.get().getOrders().add(order);
                orderRepository.save(order);
            }
        } else {
            for (Order order : user.get().getOrders()) {
                if (!order.getStatus()) {
                    if (productRepository.findById(id).get().getQuantity() == 0) {
                        return "noProductInStock";
                    } else {
                        order.getProducts().add(productRepository.findById(id).get());
                        orderRepository.save(order);
                    }
                } else {
                    Order order1 = new Order(false, user.get());
                    if (productRepository.findById(id).get().getQuantity() == 0) {
                        return "noProductInStock";
                    } else {
                        order1.getProducts().add(productRepository.findById(id).get());
                        user.get().getOrders().add(order1);
                        orderRepository.save(order);
                    }
                }

            }
        }
        return "redirect:/index";
    }
}

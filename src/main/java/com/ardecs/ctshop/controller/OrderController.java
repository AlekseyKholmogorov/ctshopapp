package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import com.ardecs.ctshop.service.OrderService;
import com.ardecs.ctshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OrderController {

    //Временно, пока не выполню #23
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {

        this.orderService = orderService;
        this.orderRepository = orderRepository;

    }

    @GetMapping("user/userInfo/order/{id}")
    public String showOrder(@PathVariable("id") Integer id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("totalSum", orderService.getTotalSum(order));
        model.addAttribute("order", order);
        model.addAttribute("products", orderService.getProductsInOrder(order));
        return "user/order";
    }

    @GetMapping("deleteOrder/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        orderService.deleteOrder(order);
        return "redirect:/index";
    }

    @PostMapping("user/userInfo/order/{id}/buy")
    public String buy(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        order.setIsPaid(true);
        orderRepository.save(order);
        return "redirect:/index";
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


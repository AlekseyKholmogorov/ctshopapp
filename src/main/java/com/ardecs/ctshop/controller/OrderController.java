package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import com.ardecs.ctshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("user")
public class OrderController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(UserRepository userRepository, OrderService orderService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;

    }

    @GetMapping("/userInfo/order/{id}")
    public String showOrder(@PathVariable("id") Integer id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("totalSum", orderService.getTotalSum(order));
        model.addAttribute("order", order);
        model.addAttribute("products", orderService.getProductsInOrder(order));
        return "user/order";
    }


    @GetMapping("/userInfo/order/shoppingCart")
    public String showShoppingCart(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        Order notPaidOrder = user.getOrders().stream().filter(o -> !o.getIsPaid()).findAny().orElse(null);

        if (notPaidOrder == null) {
            return "redirect:/index";
        }

        model.addAttribute("totalSum", orderService.getTotalSum(notPaidOrder));
        model.addAttribute("order", notPaidOrder);
        model.addAttribute("products", orderService.getProductsInOrder(notPaidOrder));

        return "user/order";
    }

    @GetMapping("/userInfo/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        orderService.deleteOrder(order);
        return "redirect:/index";
    }

    @PostMapping("/userInfo/order/{id}/buy")
    public String confirmOrder(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        order.setIsPaid(true);
        orderRepository.save(order);
        return "redirect:/index";
    }

    @GetMapping("addProductToOrder/{id}")
    public String addProductToOrder(@PathVariable("id") Integer id, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());
        return orderService.addProductToOrder(user, id);

    }
}


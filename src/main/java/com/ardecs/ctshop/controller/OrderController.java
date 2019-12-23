package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.OrderProduct;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {

        this.orderRepository = orderRepository;

        this.productRepository = productRepository;
    }

    @GetMapping("user/userInfo/order/{id}")
    public String showOrder(@PathVariable("id") Integer id, Model model) {
        Optional<Order> order = orderRepository.findById(id);
        Map<Product, Integer> products = new HashMap();
        Double totalSum = 0.0;
        for (OrderProduct orderProduct : order.get().getOrderProducts()) {
            products.put(orderProduct.getProduct(), orderProduct.getQuantityInOrder());
            totalSum += orderProduct.getProduct().getPrice() * orderProduct.getQuantityInOrder();
        }
        model.addAttribute("totalSum", totalSum);
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        return "user/order";
    }

    @GetMapping("deleteOrder/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id).get();
        if (!order.getStatus()) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = productRepository.findById(orderProduct.getProduct().getId()).get();
                product.setQuantity(product.getQuantity() + orderProduct.getQuantityInOrder());
                productRepository.save(product);
            }
        }
        orderRepository.deleteById(id);
        return "redirect:/index";
    }

    @PostMapping("user/userInfo/order/{id}/buy")
    public String buy(@PathVariable("id") Integer id) {
        orderRepository.findById(id).get().setStatus(true);
        orderRepository.save(orderRepository.findById(id).get());
        return "redirect:/index";
    }
}


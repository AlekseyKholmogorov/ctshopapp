package com.ardecs.ctshop.service;

import com.ardecs.ctshop.exceptions.PaidOrderCannotBeDeletedException;
import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Map<Product, Integer> getProductsInOrder(Order order) {
        Map<Product, Integer> products = new HashMap<>();
        order.getOrderProducts().forEach(p -> products.put(p.getProduct(), p.getQuantityInOrder()));
        return products;
    }

    public BigDecimal getTotalSum(Order order) {
        List<BigDecimal> sums = new ArrayList<>();
        order.getOrderProducts().forEach(p -> sums.add(p.getProduct().getPrice().multiply(BigDecimal.valueOf(p.getQuantityInOrder()))));
        return sums.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deleteOrder(Order order) {

        if (order.getIsPaid()) {
            throw new PaidOrderCannotBeDeletedException("Paid order cannot be deleted");
        }

        order.getOrderProducts().forEach(
                p -> p.getProduct().setQuantity(p.getProduct().getQuantity() + p.getQuantityInOrder())
        );

        orderRepository.delete(order);
    }

}

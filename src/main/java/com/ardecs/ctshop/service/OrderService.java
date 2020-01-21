package com.ardecs.ctshop.service;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.OrderProduct;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Map<Product, Integer> getProducts(Order order) {
        Map<Product, Integer> products = new HashMap<>();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            products.put(orderProduct.getProduct(), orderProduct.getQuantityInOrder());
        }

        return products;
    }

    public BigDecimal getTotalSum(Order order) {
        BigDecimal totalSum = BigDecimal.valueOf(0);
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            totalSum = new BigDecimal
                    (String.valueOf
                            (totalSum.add(BigDecimal.valueOf(orderProduct.getProduct().getPrice())
                                    .multiply(BigDecimal.valueOf(orderProduct.getQuantityInOrder())))));
        }
        return totalSum;

    }

    public void deleteOrder(Order order) {

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = productRepository.findById(orderProduct.getProduct().getId())
                    .orElseThrow(NotFoundException::new);
            product.setQuantity(product.getQuantity() + orderProduct.getQuantityInOrder());
            productRepository.save(product);
        }
        orderRepository.delete(order);
    }

}

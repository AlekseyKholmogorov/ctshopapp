package com.ardecs.ctshop.service;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.*;
import com.ardecs.ctshop.persistence.repository.OrderProductRepository;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public UserService(ProductRepository productRepository,
                       OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public String buyProduct(User user, Integer id) {

        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!isProductAvailable(product)) {
            return "/noProductInStock";
        }

        Order order = orderRepository.findByIsPaid(false);

        if (order == null ) {
            addOrder(user, product);
            return "redirect:/index";
        }

        product.setQuantity(product.getQuantity() - 1);
        OrderProductId orderProductId = new OrderProductId(order.getId(), product.getId());
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(new OrderProduct(order, product));
        order.getOrderProducts().remove(orderProduct);
        orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
        orderProductRepository.save(orderProduct);
        order.getOrderProducts().add(orderProduct);

        return "redirect:/index";

    }

    private boolean isProductAvailable(Product product) {
        return product.getQuantity() != 0;
    }

    private void addOrder(User user, Product product) {
        Order order = new Order(false, user);
        orderRepository.save(order);
        user.getOrders().add(order);
        OrderProduct orderProduct = new OrderProduct(order, product);
        product.setQuantity(product.getQuantity() - 1);
        orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
        orderProductRepository.save(orderProduct);
        order.getOrderProducts().add(orderProduct);
    }

}

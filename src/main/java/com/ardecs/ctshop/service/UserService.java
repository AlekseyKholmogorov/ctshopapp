package com.ardecs.ctshop.service;

import com.ardecs.ctshop.persistence.entity.*;
import com.ardecs.ctshop.persistence.repository.OrderProductRepository;
import com.ardecs.ctshop.persistence.repository.OrderRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public UserService(UserRepository userRepository, ProductRepository productRepository,
                       OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public String buyProduct(User user, Integer id) {

        Optional<Product> product = productRepository.findById(id);
        if (user.getOrders().size() == 0) {
            Order order = new Order(false, user);
            orderRepository.save(order);
            user.getOrders().add(order);
            OrderProduct orderProduct = new OrderProduct(order, product.get());

            if (product.get().getQuantity() == 0) {
                return "noProductInStock";
            } else {

                product.get().setQuantity(product.get().getQuantity() - 1);
                orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
                orderProductRepository.save(orderProduct);
                order.getOrderProducts().add(orderProduct);
            }
        } else {

            Order order = orderRepository.findByStatus(false);
            if (order == null ) {
                Order order1 = new Order(false, user);
                orderRepository.save(order1);
                user.getOrders().add(order1);
                OrderProduct orderProduct = new OrderProduct(order1, product.get());
                if (productRepository.findById(id).get().getQuantity() == 0) {
                    return "noProductInStock";
                } else {
                    product.get().setQuantity(product.get().getQuantity() - 1);
                    orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
                    orderProductRepository.save(orderProduct);
                    order1.getOrderProducts().add(orderProduct);
                }
            } else {
                if (product.get().getQuantity() == 0) {
                    return "noProductInStock";
                } else {
                    product.get().setQuantity(product.get().getQuantity() - 1);
                    OrderProductId orderProductId = new OrderProductId(order.getId(), product.get().getId());
                    if (orderProductRepository.findById(orderProductId).isPresent() &&
                            orderProductRepository.findById(orderProductId).get().getProduct().getId().equals(product.get().getId()) &&
                            orderProductRepository.findById(orderProductId).get().getOrder().getId().equals(order.getId())) {
                        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).get();
                        order.getOrderProducts().remove(orderProduct);
                        orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
                        orderProductRepository.save(orderProduct);
                        order.getOrderProducts().add(orderProduct);

                    } else {
                        OrderProduct orderProduct1 = new OrderProduct(order, product.get());
                        orderProduct1.setQuantityInOrder(orderProduct1.getQuantityInOrder() + 1);
                        orderProductRepository.save(orderProduct1);
                        order.getOrderProducts().add(orderProduct1);

                    }
                    orderRepository.save(order);
                }
            }

        }

        return "";

    }

}

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
            OrderProduct orderProduct = new OrderProduct(order, product.get());

            if (product.get().getQuantity() == 0) {
                return "noProductInStock";
            } else {

                productRepository.findById(id).get().setQuantity(productRepository.findById(id).get().getQuantity() - 1);
                orderProduct.setQuantityInOrder(orderProduct.getQuantityInOrder() + 1);
                order.getOrderProducts().add(orderProduct);
                user.getOrders().add(order);
                orderRepository.save(order);
                orderProductRepository.save(orderProduct);
            }
        } else {
            for (Order o : user.getOrders()) {
                if (!o.getStatus()) {
                    if (productRepository.findById(id).get().getQuantity() == 0) {
                        return "noProductInStock";
                    } else {
                        product.get().setQuantity(productRepository.findById(id).get().getQuantity() - 1);

                        if (orderProductRepository.findByOrderAndProduct(o, product.get()).getProduct().getId().equals(product.get().getId())) {
                            orderProductRepository.findByOrderAndProduct(o, product.get()).setQuantityInOrder(orderProductRepository.findByOrder(o).getQuantityInOrder() + 1);
                            orderProductRepository.save(orderProductRepository.findByOrder(o));
                        } else {
                            OrderProduct orderProduct1 = new OrderProduct(o, product.get());
                            orderProduct1.setQuantityInOrder(orderProduct1.getQuantityInOrder() + 1);
                            o.getOrderProducts().add(orderProduct1);
                            orderProductRepository.save(orderProduct1);
                            //orderProductRepository.findByProduct(productRepository.findById(id).get()).setQuantityInOrder(orderProductRepository.findByOrderAndProduct(o, productRepository.findById(id).get()).getQuantityInOrder() + 1);
                            //o.getOrderProducts().add(orderProductRepository.findByOrderAndProduct(o, productRepository.findById(id).get()));
                            //orderProductRepository.save(orderProductRepository.findByOrderAndProduct(o, productRepository.findById(id).get()));
                        }
                        orderRepository.save(o);
                    }
                } else {
                    Order order1 = new Order(false, user);
                    if (productRepository.findById(id).get().getQuantity() == 0) {
                        return "noProductInStock";
                    } else {
                        productRepository.findById(id).get().setQuantity(productRepository.findById(id).get().getQuantity() - 1);
                        orderRepository.save(order1);
                    }
                }

            }
        }
        return "";
    }
}

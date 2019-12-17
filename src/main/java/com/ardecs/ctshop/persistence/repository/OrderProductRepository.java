package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Order;
import com.ardecs.ctshop.persistence.entity.OrderProduct;
import com.ardecs.ctshop.persistence.entity.OrderProductId;
import com.ardecs.ctshop.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    OrderProduct findByOrderAndProduct(Order order, Product product);
    OrderProduct findByOrder(Order order);
    OrderProduct findByProduct(Product product);
    Optional<OrderProduct> findById(OrderProductId orderProductId);
}

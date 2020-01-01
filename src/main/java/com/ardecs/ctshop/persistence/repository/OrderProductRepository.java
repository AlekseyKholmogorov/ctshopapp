package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.OrderProduct;
import com.ardecs.ctshop.persistence.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}

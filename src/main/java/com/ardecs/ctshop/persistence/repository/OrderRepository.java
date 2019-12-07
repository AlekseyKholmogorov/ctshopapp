package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

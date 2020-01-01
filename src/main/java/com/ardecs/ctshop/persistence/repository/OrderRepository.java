package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByIsPaid(boolean isPaid);

}

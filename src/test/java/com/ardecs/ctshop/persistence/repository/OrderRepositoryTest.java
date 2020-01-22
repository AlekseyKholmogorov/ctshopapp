package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void findByIsPaid() {

        Order order = new Order();
        order.setIsPaid(false);
        orderRepository.save(order);
        Order order1 = orderRepository.findByIsPaid(false);
        assertNotNull(order);
        assertNotNull(order1);
        assertEquals(order.getIsPaid(), order1.getIsPaid());

    }
}
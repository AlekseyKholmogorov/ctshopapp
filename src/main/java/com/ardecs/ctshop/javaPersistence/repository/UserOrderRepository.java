package com.ardecs.ctshop.javaPersistence.repository;

import com.ardecs.ctshop.javaPersistence.entity.UserOrder;
import org.springframework.data.repository.CrudRepository;

public interface UserOrderRepository extends CrudRepository<UserOrder, Integer> {
}

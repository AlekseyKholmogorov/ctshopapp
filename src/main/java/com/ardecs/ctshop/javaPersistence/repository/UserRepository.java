package com.ardecs.ctshop.javaPersistence.repository;

import com.ardecs.ctshop.javaPersistence.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}

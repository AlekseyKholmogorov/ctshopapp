package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}

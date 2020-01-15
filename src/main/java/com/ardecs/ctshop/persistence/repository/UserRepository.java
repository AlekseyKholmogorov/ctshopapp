package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);
}

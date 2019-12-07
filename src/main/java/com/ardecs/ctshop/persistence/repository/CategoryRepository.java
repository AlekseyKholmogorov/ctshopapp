package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

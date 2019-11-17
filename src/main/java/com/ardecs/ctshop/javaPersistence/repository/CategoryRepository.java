package com.ardecs.ctshop.javaPersistence.repository;

import com.ardecs.ctshop.javaPersistence.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}

package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameLike(String name);
    List<Product> findAllByCategory(Category category) ;

}

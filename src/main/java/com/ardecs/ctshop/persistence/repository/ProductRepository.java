package com.ardecs.ctshop.persistence.repository;

import com.ardecs.ctshop.annotations.Loggable;
import com.ardecs.ctshop.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);

    @Query("from Product p where p.category.name = 'coffee'")
    List<Product> findProductsByCategoryCoffee();

    @Query("from Product p where p.category.name = 'tea'")
    List<Product> findProductsByCategoryTea();

    @Query("from Product p where p.category.name = 'accessories'")
    List<Product> findProductsByCategoryAccessories();

    @Loggable
    @Query("from Product p where p.quantity > 0 order by p.price")
    List<Product> findProductsByAvailabilityAndSortByPrice();
}

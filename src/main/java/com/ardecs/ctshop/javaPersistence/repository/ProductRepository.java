package com.ardecs.ctshop.javaPersistence.repository;

import com.ardecs.ctshop.javaPersistence.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findByName(String name);

    @Query("from Product p where quantity > 0 order by p.price")
    List<Product> findProductsByAvailabilityAndSortByPrice();

}

package com.ardecs.ctshopapp.accessingdatamysql.repo;

import com.ardecs.ctshopapp.accessingdatamysql.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {

    Product findByName(String name);

    @Query("from Product p where quantity > 0 order by p.price")
    List<Product> findProductsByAvailabilityAndSortByPrice();

}

package com.ardecs.ctshopapp;

import com.ardecs.ctshopapp.accessingdatamysql.Product;
import com.ardecs.ctshopapp.accessingdatamysql.repo.ProductRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)

@SpringBootTest

public class CtshopappApplicationTests {


    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
    public void testFindByName() {
        Product p = productRepo.findByName("bucaramanga");
        System.out.println("------" + p.getName().toUpperCase() + "------");
    }

    @Test
    @Transactional
    public void testFindProductsByAvailabilityAndSortByPrice() {
        List<Product> products = productRepo.findProductsByAvailabilityAndSortByPrice();
        products.forEach(p -> System.out.println("------" + p.getName().toUpperCase() + " " + p.getPrice() + "------"));
    }

    @Test
    @Transactional
    public void testProductEntity() {
        Product p = new Product("terrazu valley", "costa rica", "coffee from costa rica",
                                28.30, 5);
        productRepo.findByName("terrazu valley");
        assertThat(p.getName()).isEqualTo("terrazu valley");
    }

}


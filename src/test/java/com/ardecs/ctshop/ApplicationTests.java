package com.ardecs.ctshop;

import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)

@SpringBootTest

public class ApplicationTests {


    @Autowired
    private MyService myService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testFindByName() {
        Product p = productRepository.findByName("bucaramanga");
        System.out.println("------" + p.getName().toUpperCase() + "------");
    }

    @Test
    @Transactional
    public void testFindProductsByAvailabilityAndSortByPrice() {
        List<Product> products = productRepository.findProductsByAvailabilityAndSortByPrice();
        products.forEach(p -> System.out.println("------" + p.getName().toUpperCase() + " " + p.getPrice() + "------"));
    }

    @Test
    @Transactional
    public void testFindProductsByCategoryCoffee() {
        List<Product> products = productRepository.findProductsByCategoryCoffee();
        products.forEach(p -> System.out.println("------" + p.getName().toUpperCase() + " " + p.getCategory() + "------"));
    }

    @Test
    public void testLoggable() throws InterruptedException {
        myService.addCategory("coffee");
        myService.showCategories();
        myService.addCategory("tea");
        myService.showCategories();
        myService.addCategory("accessories");
        myService.showCategories();
    }
}


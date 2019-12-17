package com.ardecs.ctshop.service;

import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.entity.Role;
import com.ardecs.ctshop.persistence.entity.User;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import com.ardecs.ctshop.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInitData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DbInitData(CategoryRepository categoryRepository, ProductRepository productRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) {

        Category cat1 = new Category("coffee");
        Category cat2 = new Category("tea");
        Category cat3 = new Category("accessories");
        List<Category> categoryList = Arrays.asList(cat1, cat2, cat3);
        this.categoryRepository.saveAll(categoryList);

        Product product1 = new Product("assam", "india", "black tea from india", 21.95, 3, cat2);
        Product product2 = new Product("lapsang souchong", "china", "black tea from china", 9.98, 2, cat2);
        Product product3 = new Product("bucaramanga", "colombia", "coffee from colombia", 26.40, 5, cat1);
        Product product4 = new Product("amecafe", "brazil", "coffee from brazil", 18.75, 4, cat1);
        List<Product> productList = Arrays.asList(product1, product2, product3, product4);
        this.productRepository.saveAll(productList);

        User user1 = new User("admin", passwordEncoder.encode("admin"), "admin@example.com");
        user1.getRoles().add(Role.ADMIN);
        User user2 = new User("john", passwordEncoder.encode("123"), "john@example.com");
        user2.getRoles().add(Role.USER);
        List<User> userList = Arrays.asList(user1, user2);
        this.userRepository.saveAll(userList);
    }
}

package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.entity.Product;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import com.ardecs.ctshop.persistence.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;



    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/product")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/product";
    }

    @GetMapping("/showFormForAddProduct")
    public String showFormForAdd(Model model) {
        Category category = new Category();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/productForm";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        if (bindingResult.hasErrors()) {
            return "admin/productForm";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("showFormForUpdateProduct/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {

        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product.get());
        model.addAttribute("category", product.get().getCategory().getName());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/productForm";
    }
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
    @GetMapping("/showInfoAboutProduct/{id}")
    public String showProductInfo(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "productInfo";
    }
}

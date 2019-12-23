package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("category")
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @GetMapping("/showFormForAddCategory")
    public String showFormForAdd(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/categoryForm";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categoryForm";
        }
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @GetMapping("showFormForUpdateCategory/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);
        model.addAttribute("category", category);
        return "admin/categoryForm";
    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }
}

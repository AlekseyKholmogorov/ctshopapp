package com.ardecs.ctshop.controller;

import com.ardecs.ctshop.exceptions.NotFoundException;
import com.ardecs.ctshop.persistence.entity.Category;
import com.ardecs.ctshop.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/category";
    }

    @GetMapping("/showFormForAddCategory")
    public String showFormForAdd(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categoryForm";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categoryForm";
        }
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/showFormForUpdateCategory/{id}")
    public String showFormForUpdate(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("category", category);
        return "admin/categoryForm";
    }
    @GetMapping("/category/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }
}

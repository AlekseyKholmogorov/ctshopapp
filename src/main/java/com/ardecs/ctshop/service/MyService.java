package com.ardecs.ctshop.service;

import com.ardecs.ctshop.annotations.Loggable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {

    private List<String> categoryList = new ArrayList<>();

    public void addCategory(String category) {
        categoryList.add(category);
        System.out.println("Added new category: " + category);
    }

    @Loggable
    public void showCategories() throws InterruptedException {
        for (String category : categoryList) {
            System.out.println(category);
            Thread.sleep(10);
        }
    }
}

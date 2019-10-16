package com.ardecs.ctshopapp.accessingdatamysql.repo;

import com.ardecs.ctshopapp.accessingdatamysql.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Integer> {
}

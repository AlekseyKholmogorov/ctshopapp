package com.ardecs.ctshopapp.accessingdatamysql.repo;

import com.ardecs.ctshopapp.accessingdatamysql.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
}

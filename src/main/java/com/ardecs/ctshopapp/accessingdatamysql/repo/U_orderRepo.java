package com.ardecs.ctshopapp.accessingdatamysql.repo;

import com.ardecs.ctshopapp.accessingdatamysql.U_order;
import org.springframework.data.repository.CrudRepository;

public interface U_orderRepo extends CrudRepository<U_order, Integer> {
}

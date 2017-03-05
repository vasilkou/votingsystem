package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {
}

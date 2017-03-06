package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Dish;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> findMenuAndSort(@Param("restaurantId") int restaurantId, Sort sort);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.restaurant.id=:restaurantId")
    int deleteMenu(@Param("restaurantId") int restaurantId);
}

package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Dish;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 06-Mar-17.
 */
public interface DishRepository {
    // null if updated dish do not belong to restaurant
    Dish save(Dish dish, int restaurantId);

    List<Dish> saveMenu(List<Dish> menu, Integer restaurantId);

    // null if dish do not belong to restaurant
    Dish get(int id, int restaurantId);

    // ORDERED by name ASC
    List<Dish> getMenu(int restaurantId);

    // false if dish do not belong to restaurant
    boolean delete(int id, int restaurantId);

    boolean deleteMenu(int restaurantId);
}

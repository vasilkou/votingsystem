package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.util.exceptions.NotFoundException;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public interface DishService {
    Dish save(Dish dish, Integer restaurantId);

    List<Dish> saveMenu(List<Dish> menu, Integer restaurantId);

    Dish get(int id) throws NotFoundException;

    List<Dish> getMenu(int restaurantId);

    void update(Dish dish);

    void updateMenu(List<Dish> menu, int restaurantId);

    void delete(int id) throws NotFoundException;

    void deleteMenu(int restaurantId) throws NotFoundException;
}

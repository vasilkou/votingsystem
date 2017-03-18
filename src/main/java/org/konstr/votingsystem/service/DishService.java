package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.util.exceptions.NotFoundException;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public interface DishService {
    Dish save(Dish dish, int restaurantId);

    Dish get(int id, int restaurantId) throws NotFoundException;

    List<Dish> getMenu(int restaurantId);

    Dish update(Dish dish, int restaurantId);

    void delete(int id, int restaurantId) throws NotFoundException;

    void deleteMenu(int restaurantId) throws NotFoundException;
}

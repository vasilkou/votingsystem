package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Service
public class DishServiceImpl implements DishService {
    @Override
    public Dish save(Dish dish, Integer restaurantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Dish> saveMenu(List<Dish> menu, Integer restaurantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dish get(int id) throws NotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Dish> getMenu(int restaurantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Dish dish) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateMenu(List<Dish> menu, int restaurantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteMenu(int restaurantId) throws NotFoundException {
        throw new UnsupportedOperationException();
    }
}

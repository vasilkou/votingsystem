package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.repository.DishRepository;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository repository;

    @Override
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, restaurantId);
    }

    @Override
    public List<Dish> saveMenu(List<Dish> menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.saveMenu(menu, restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Override
    public List<Dish> getMenu(int restaurantId) {
        return checkNotFoundWithId(repository.getMenu(restaurantId), restaurantId);
    }

    @Override
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @Override
    public List<Dish> updateMenu(List<Dish> menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return checkNotFoundWithId(repository.saveMenu(menu, restaurantId), restaurantId);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Override
    public void deleteMenu(int restaurantId) throws NotFoundException {
        checkNotFoundWithId(repository.deleteMenu(restaurantId), restaurantId);
    }
}

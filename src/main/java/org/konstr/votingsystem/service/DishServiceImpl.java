package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.repository.DishRepository;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkNotFound;
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
        return checkNotFound(repository.save(dish, restaurantId), "restaurantId=" + restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) throws NotFoundException {
        return checkNotFound(repository.get(id, restaurantId), "id=" + id + ", restaurantId=" + restaurantId);
    }

    @Override
    public List<Dish> getMenu(int restaurantId) {
        return repository.getMenu(restaurantId);
    }

    @Override
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFound(repository.save(dish, restaurantId), "id=" + dish.getId() + ", restaurantId=" + restaurantId);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFound(repository.delete(id, restaurantId), "id=" + id + ", restaurantId=" + restaurantId);
    }

    @Override
    public void deleteMenu(int restaurantId) throws NotFoundException {
        checkNotFoundWithId(repository.deleteMenu(restaurantId), restaurantId);
    }
}

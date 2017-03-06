package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yury Vasilkou
 * Date: 06-Mar-17.
 */
@Repository
public class DishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return repository.save(dish);
    }

    @Transactional
    @Override
    public List<Dish> saveMenu(List<Dish> menu, Integer restaurantId) {
        return menu.stream()
                .map(dish -> save(dish, restaurantId))
                .collect(Collectors.toList());
    }

    @Override
    public Dish get(int id, int restaurantId) {
        Dish dish = repository.findOne(id);
        return dish != null && dish.getRestaurant().getId() == restaurantId ? dish : null;
    }

    @Override
    public List<Dish> getMenu(int restaurantId) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
        return repository.findMenuAndSort(restaurantId, new Sort(order));
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return repository.delete(id, restaurantId) != 0;
    }

    @Override
    public boolean deleteMenu(int restaurantId) {
        return repository.deleteMenu(restaurantId) != 0;
    }
}

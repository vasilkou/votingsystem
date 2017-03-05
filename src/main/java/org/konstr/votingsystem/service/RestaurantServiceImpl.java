package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.repository.DishRepository;
import org.konstr.votingsystem.repository.RestaurantRepository;
import org.konstr.votingsystem.to.RestaurantTo;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Yury Vasilkou
 * Date: 03-Mar-17.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {
//    private static final Sort SORT_NAME = new Sort("name");

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private DishRepository dishRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public List<RestaurantTo> getAll() {
// return TO
        return /*repository.getAllWithMenu()*/ null;
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Restaurant restaurant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        throw new UnsupportedOperationException();
    }
}

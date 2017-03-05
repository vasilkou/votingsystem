package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.to.RestaurantTo;
import org.konstr.votingsystem.util.exceptions.NotFoundException;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    List<RestaurantTo> getAll();

    Restaurant get(int id) throws NotFoundException;

    Restaurant getByName(String name) throws NotFoundException;

    void update(Restaurant restaurant);

    void delete(int id) throws NotFoundException;
}

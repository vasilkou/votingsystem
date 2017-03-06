package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.repository.RestaurantRepository;
import org.konstr.votingsystem.repository.VoteRepository;
import org.konstr.votingsystem.to.RestaurantTo;
import org.konstr.votingsystem.util.RestaurantUtil;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkNotFound;
import static org.konstr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Yury Vasilkou
 * Date: 03-Mar-17.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    @Override
    public List<RestaurantTo> getAll(int userId) {
        return RestaurantUtil.getWithVoteResults(
                repository.getAllWithMenu(),
                voteRepository.findSelectedRestaurantId(userId, LocalDate.now())
        );
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.findByName(name), "name=" + name);
    }

    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        repository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}

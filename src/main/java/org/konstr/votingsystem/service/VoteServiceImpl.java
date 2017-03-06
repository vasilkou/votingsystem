package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.model.VoteResult;
import org.konstr.votingsystem.repository.RestaurantRepository;
import org.konstr.votingsystem.repository.UserRepository;
import org.konstr.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Service
public class VoteServiceImpl implements VoteService {
    public static final Comparator<VoteResult> VOTES_DESC = Comparator.comparing(VoteResult::getVotes).reversed().thenComparing(VoteResult::getRestaurantName);

    @Autowired
    private VoteRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void vote(int restaurantId, int userId) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findOne(restaurantId), restaurantId);

        Vote vote = repository.findByUserId(userId, LocalDate.now());
        if (vote == null) {
            vote = new Vote(null, userRepository.getOne(userId));
        }
        vote.setRestaurant(restaurant);
        vote.setRestaurantName(restaurant.getName());

        repository.save(vote);
    }

    @Override
    public List<VoteResult> getCurrentResults() {
        return getResultsByDate(LocalDate.now());
    }

    @Override
    public List<VoteResult> getResultsByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        List<VoteResult> result = repository.getVotesByDate(date);
        result.sort(VOTES_DESC);
        return result;
    }
}

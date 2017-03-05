package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.to.VotingResults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Override
    public Vote vote(int restaurantId, int userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VotingResults getCurrentResults() {
        throw new UnsupportedOperationException();
    }

    @Override
    public VotingResults getResultsByDate(LocalDate date) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<VotingResults> getFilteredResults(LocalDate start, LocalDate end) {
        throw new UnsupportedOperationException();
    }
}

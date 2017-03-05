package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.to.VotingResults;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public interface VoteService {

    Vote vote(int restaurantId, int userId);

    VotingResults getCurrentResults();

    VotingResults getResultsByDate(LocalDate date);

    List<VotingResults> getFilteredResults(LocalDate start, LocalDate end);
}

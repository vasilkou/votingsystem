package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.VoteResult;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public interface VoteService {
    void vote(int restaurantId, int userId);

    List<VoteResult> getCurrentResults();

    List<VoteResult> getResultsByDate(LocalDate date);
}

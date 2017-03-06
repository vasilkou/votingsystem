package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.model.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT new org.konstr.votingsystem.model.VoteResult(v.restaurantName, count(v.id), v.date)" +
            "FROM Vote v WHERE v.date=:date GROUP BY v.restaurant.id, v.restaurantName, v.date")
    List<VoteResult> getVotesByDate(@Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.voter.id=:userId AND v.date=:date")
    Vote findByUserId(@Param("userId") int userId, @Param("date") LocalDate date);

    @Transactional
    @Override
    Vote save(Vote vote);

    @Query("SELECT v.restaurant.id FROM Vote v WHERE v.voter.id=:userId AND v.date=:date")
    Integer findSelectedRestaurantId(@Param("userId") int userId, @Param("date") LocalDate date);
}

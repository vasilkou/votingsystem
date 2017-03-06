package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v.restaurant FROM Vote v WHERE v.voter=:userId AND v.date=:date")
    Integer findSelectedRestaurantId(@Param("userId") int userId, @Param("date") LocalDate date);
}

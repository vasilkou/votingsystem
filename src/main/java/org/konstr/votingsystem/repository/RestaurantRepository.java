package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
/*
    @Override
    List<Restaurant> findAll(Sort sort);
*/

    @Override
    Restaurant findOne(Integer id);

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu ORDER BY r.name ASC")
    List<Restaurant> getAllWithMenu();
}

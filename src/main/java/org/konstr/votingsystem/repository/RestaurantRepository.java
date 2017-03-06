package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Override
    Restaurant findOne(Integer id);

    Restaurant findByName(String name);

    @Override
    Restaurant getOne(Integer integer);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu")
    List<Restaurant> getAllWithMenu();

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);
}

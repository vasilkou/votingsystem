package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Override
    User save(User user);

    @Override
    List<User> findAll(Sort sort);

    @Override
    User findOne(Integer id);

    @Override
    User getOne(Integer integer);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email")
    User findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
}

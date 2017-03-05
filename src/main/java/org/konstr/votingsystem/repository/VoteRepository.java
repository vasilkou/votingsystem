package org.konstr.votingsystem.repository;

import org.konstr.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}

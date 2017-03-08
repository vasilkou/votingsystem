package org.konstr.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.konstr.votingsystem.RestaurantTestData;
import org.konstr.votingsystem.TestUtil;
import org.konstr.votingsystem.model.VoteResult;
import org.konstr.votingsystem.util.VoteUtil;
import org.konstr.votingsystem.util.exceptions.VoteForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.konstr.votingsystem.VoteTestData.*;
import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetCurrentResults() throws Exception {
        List<VoteResult> current = service.getCurrentResults();
        MATCHER.assertCollectionEquals(CURRENT_RESULTS, current);
    }

    @Test
    public void testGetResultsByDate() throws Exception {
        List<VoteResult> results = service.getResultsByDate(LocalDate.now());
        MATCHER.assertCollectionEquals(CURRENT_RESULTS, results);
    }

    @Test
    public void testGetResultsByDateWrongDate() throws Exception {
        List<VoteResult> results = service.getResultsByDate(LocalDate.of(2016, Month.MAY, 22));
        MATCHER.assertCollectionEquals(Collections.emptyList(), results);
    }

    @Test
    public void testVoteAndChangeBefore() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().plusMinutes(1));

        vote();
        changeVote();
    }

    @Test
    public void testVoteAfter() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().minusMinutes(1));

        vote();
    }

    @Test(expected = VoteForbiddenException.class)
    public void testVoteChangeForbiddenAfter() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().minusMinutes(1));

        vote();
        changeVote();
    }

    private void vote() {
        service.vote(RestaurantTestData.RESTAURANT_2_ID, START_SEQ + 13);
        List<VoteResult> current = service.getCurrentResults();
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_NEW_VOTE, current);
    }

    private void changeVote() {
        service.vote(RestaurantTestData.RESTAURANT_1_ID, START_SEQ + 13);
        List<VoteResult> current = service.getCurrentResults();
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_UPDATE_VOTE, current);
    }
}

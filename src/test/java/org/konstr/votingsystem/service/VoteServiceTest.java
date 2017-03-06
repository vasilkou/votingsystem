package org.konstr.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konstr.votingsystem.RestaurantTestData;
import org.konstr.votingsystem.model.VoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.konstr.votingsystem.VoteTestData.*;
import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

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
    public void testVote() throws Exception {
        service.vote(RestaurantTestData.RESTAURANT_2_ID, START_SEQ + 13);
        List<VoteResult> current = service.getCurrentResults();
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_NEW_VOTE, current);

        service.vote(RestaurantTestData.RESTAURANT_1_ID, START_SEQ + 13);
        current = service.getCurrentResults();
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_UPDATE_VOTE, current);
    }
}

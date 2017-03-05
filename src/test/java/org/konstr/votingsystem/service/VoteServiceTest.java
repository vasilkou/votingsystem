package org.konstr.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konstr.votingsystem.RestaurantTestData;
import org.konstr.votingsystem.UserTestData;
import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.to.VotingResults;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;

import static org.konstr.votingsystem.TestUtil.validateRootCause;
import static org.konstr.votingsystem.VoteTestData.FILTERED_RESULTS;
import static org.konstr.votingsystem.VoteTestData.MATCHER;
import static org.konstr.votingsystem.VoteTestData.RESULTS;

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
        service.vote(RestaurantTestData.RESTAURANT_1_ID, UserTestData.USER_ID);
        service.vote(RestaurantTestData.RESTAURANT_2_ID, UserTestData.ADMIN_ID);
        VotingResults current = service.getCurrentResults();
        MATCHER.assertEquals(RESULTS, current);
    }

    @Test
    public void testGetResultsByDate() throws Exception {
        VotingResults results = service.getResultsByDate(LocalDate.of(2017, Month.FEBRUARY, 27));
        MATCHER.assertEquals(RESULTS, results);
    }

    @Test(expected = NotFoundException.class)
    public void testGetResultsByDateNotFound() throws Exception {
        service.getResultsByDate(LocalDate.of(2016, Month.MAY, 22));
    }

    @Test
    public void testGetFilteredResults() throws Exception {
        List<VotingResults> filtered = service.getFilteredResults(
                LocalDate.of(2017, Month.FEBRUARY, 27),
                LocalDate.of(2017, Month.FEBRUARY, 28)
        );
        MATCHER.assertCollectionEquals(FILTERED_RESULTS, filtered);
    }

    @Test
    public void testVote() throws Exception {
        service.vote(RestaurantTestData.RESTAURANT_1_ID, UserTestData.USER_ID);
        service.vote(RestaurantTestData.RESTAURANT_2_ID, UserTestData.ADMIN_ID);
        VotingResults current = service.getCurrentResults();
        MATCHER.assertEquals(RESULTS, current);

        service.vote(RestaurantTestData.RESTAURANT_1_ID, UserTestData.USER_ID);
        service.vote(RestaurantTestData.RESTAURANT_1_ID, UserTestData.ADMIN_ID);
        current = service.getCurrentResults();
        MATCHER.assertEquals(new VotingResults(LocalDate.now(), new HashMap<String, Integer>() {{put("Susi Vesla", 2);}}), current);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.vote(-100, 100), ConstraintViolationException.class);
        validateRootCause(() -> service.vote(100, -100), ConstraintViolationException.class);
    }
}

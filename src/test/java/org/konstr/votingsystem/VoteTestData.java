package org.konstr.votingsystem;

import org.konstr.votingsystem.matcher.ModelMatcher;
import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.to.VotingResults;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.konstr.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static org.konstr.votingsystem.RestaurantTestData.RESTAURANT_2_ID;
import static org.konstr.votingsystem.UserTestData.ADMIN_ID;
import static org.konstr.votingsystem.UserTestData.USER_ID;
import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class VoteTestData {
    public static final int VOTE_USER_ID = START_SEQ + 16;
    public static final int VOTE_ADMIN_ID = START_SEQ + 17;

    public static final Vote USER_VOTE = new Vote(null, USER_ID, RESTAURANT_1_ID, "Susi Vesla");
    public static final Vote ADMIN_VOTE = new Vote(null, ADMIN_ID, RESTAURANT_2_ID, "Шоколадница");

    public static final VotingResults RESULTS = new VotingResults(LocalDate.now(), new HashMap<String, Integer>() {{
        put(USER_VOTE.getRestaurantName(), 1);
        put(ADMIN_VOTE.getRestaurantName(), 1);
    }});

    public static final List<VotingResults> FILTERED_RESULTS = Arrays.asList(
            new VotingResults(LocalDate.of(2017, Month.FEBRUARY, 28), new HashMap<String, Integer>() {{
                put(USER_VOTE.getRestaurantName(), 1);
                put(ADMIN_VOTE.getRestaurantName(), 1);
            }}),
            new VotingResults(LocalDate.of(2017, Month.FEBRUARY, 27), new HashMap<String, Integer>() {{
                put("Chaihana", 2);
            }})
    );

    public static final ModelMatcher<VotingResults> MATCHER = ModelMatcher.of(VotingResults.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDate(), actual.getDate())
                            && Objects.equals(expected.getResults(), actual.getResults())
                    )
    );
}

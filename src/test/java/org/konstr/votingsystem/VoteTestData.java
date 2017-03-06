package org.konstr.votingsystem;

import org.konstr.votingsystem.matcher.ModelMatcher;
import org.konstr.votingsystem.model.VoteResult;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class VoteTestData {

    public static final VoteResult VOTES_1 = new VoteResult("Susi Vesla", 1L, LocalDate.now());
    public static final VoteResult VOTES_2 = new VoteResult("Шоколадница", 2L, LocalDate.now());
    public static final VoteResult NEW_VOTES = new VoteResult("Шоколадница", 3L, LocalDate.now());
    public static final VoteResult UPDATE_VOTES = new VoteResult("Susi Vesla", 2L, LocalDate.now());

    public static final List<VoteResult> CURRENT_RESULTS = Arrays.asList(VOTES_2, VOTES_1);
    public static final List<VoteResult> CURRENT_RESULTS_AFTER_NEW_VOTE = Arrays.asList(NEW_VOTES, VOTES_1);
    public static final List<VoteResult> CURRENT_RESULTS_AFTER_UPDATE_VOTE = Arrays.asList(UPDATE_VOTES, VOTES_2);

    public static final ModelMatcher<VoteResult> MATCHER = ModelMatcher.of(VoteResult.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDate(), actual.getDate())
                            && Objects.equals(expected.getRestaurantName(), actual.getRestaurantName())
                            && Objects.equals(expected.getVotes(), actual.getVotes())
                    )
    );
}

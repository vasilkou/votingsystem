package org.konstr.votingsystem.util;

import org.konstr.votingsystem.model.Vote;
import org.konstr.votingsystem.util.exceptions.VoteForbiddenException;

import java.time.LocalTime;

/**
 * Created by Yury Vasilkou
 * Date: 08-Mar-17.
 */
public class VoteUtil {
    public static final String VOTE_FORBIDDEN_KEY = "exception.votes.forbidden";
    public static final LocalTime END_OF_REVOTING = LocalTime.of(11, 0);

    public static void checkVoteTime(Vote vote) {
        if (vote != null && LocalTime.now().isAfter(END_OF_REVOTING)) {
            throw new VoteForbiddenException(VOTE_FORBIDDEN_KEY + " " + END_OF_REVOTING);
        }
    }
}

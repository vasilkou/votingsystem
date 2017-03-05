package org.konstr.votingsystem.to;

import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public class VotingResults {
    private static final long serialVersionUID = 1L;

    private LocalDate date;

    private Map<String, Integer> results;

    public VotingResults() {
    }

    public VotingResults(LocalDate date, Map<String, Integer> results) {
        this.date = date;
        setResults(results);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Integer> getResults() {
        return results;
    }

    public void setResults(Map<String, Integer> results) {
        this.results = CollectionUtils.isEmpty(results) ? Collections.emptyMap() : results;
    }

    @Override
    public String toString() {
        return "VotingResults{" +
                "date=" + date +
                ", results=" + results +
                '}';
    }
}

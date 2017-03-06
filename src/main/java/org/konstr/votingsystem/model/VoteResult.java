package org.konstr.votingsystem.model;

import java.time.LocalDate;

/**
 * Created by Yury Vasilkou
 * Date: 06-Mar-17.
 */
public class VoteResult {
    private String restaurantName;

    private Integer votes;

    private LocalDate date;

    public VoteResult() {
    }

    public VoteResult(String restaurantName, Long votes, LocalDate date) {
        this.restaurantName = restaurantName;
        this.votes = votes.intValue();
        this.date = date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "VoteResult{" +
                "restaurantName='" + restaurantName + '\'' +
                ", votes=" + votes +
                ", date=" + date +
                '}';
    }
}

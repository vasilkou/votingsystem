package org.konstr.votingsystem.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter")
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected")
    private Restaurant restaurant;

    @NotBlank
    @SafeHtml
    @Column(name = "restaurant_name")
    private String restaurantName;

    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(Integer id, User user) {
        super(id);
        this.voter = user;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + getId() +
                ", restaurantName='" + restaurantName + '\'' +
                ", date=" + date +
                '}';
    }
}

package org.konstr.votingsystem.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    @NotNull
    @Range(min = 0)
    private Integer voter;

    @NotNull
    @Range(min = 0)
    @Column(name = "selected")
    private Integer restaurant;

    @NotBlank
    @SafeHtml
    @Column(name = "restaurant_name")
    private String restaurantName;

    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(Integer id, Integer voter, Integer restaurant, String restaurantName) {
        super(id);
        this.voter = voter;
        this.restaurant = restaurant;
        this.restaurantName = restaurantName;
    }

    public Integer getVoter() {
        return voter;
    }

    public void setVoter(Integer voter) {
        this.voter = voter;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
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
                ", voter=" + voter +
                ", restaurant=" + restaurant +
                ", restaurantName='" + restaurantName + '\'' +
                ", date=" + date +
                '}';
    }
}

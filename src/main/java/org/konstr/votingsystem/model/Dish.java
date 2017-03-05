package org.konstr.votingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@Entity
@Table(name = "dishes")
public class Dish extends NamedEntity {

    @DecimalMin("0.00")
    @DecimalMax("1_000_000.00")
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Dish d) {
        this(d.getId(), d.getName(), d.getPrice());
    }

    public Dish(Integer id, String name, Float price) {
        super(id, name);
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish (" +
                "id=" + getId() +
                ", name=" + getName() +
                ", price=" + price +
                ')';
    }
}

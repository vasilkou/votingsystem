package org.konstr.votingsystem.to;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.model.Restaurant;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class RestaurantTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String address;

    private String phoneNumber;

    private List<Dish> menu;

    private boolean selected;

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant restaurant) {
        this(restaurant, false);
    }

    public RestaurantTo(Restaurant restaurant, boolean selected) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.menu = restaurant.getMenu();
        this.selected = selected;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Collections.emptyList() : menu;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", menu=" + menu +
                ", selected=" + selected +
                '}';
    }
}

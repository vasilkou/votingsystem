package org.konstr.votingsystem.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {

    @NotBlank
    @SafeHtml
    private String address;

    @NotBlank
    @SafeHtml
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant")
    @OrderBy("name ASC")
    private List<Dish> menu;

    public Restaurant() {
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName(), r.getAddress(), r.getPhoneNumber());
    }

    public Restaurant(Integer id, String name, String address, String phoneNumber) {
        super(id, name);
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "Restaurant (" +
                "id=" + getId() +
                ", name=" + getName() +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                ')';
    }
}

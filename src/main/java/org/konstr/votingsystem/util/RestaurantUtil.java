package org.konstr.votingsystem.util;

import org.konstr.votingsystem.model.NamedEntity;
import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.to.RestaurantTo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class RestaurantUtil {
    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant);
    }

    public static RestaurantTo asTo(Restaurant restaurant, boolean selected) {
        return new RestaurantTo(restaurant, selected);
    }

    public static List<RestaurantTo> getWithVoteResults(List<Restaurant> all, Integer selectedRestaurantId) {
        return all.stream()
                .sorted(Comparator.comparing(NamedEntity::getName))
                .map(restaurant -> new RestaurantTo(restaurant, restaurant.getId().equals(selectedRestaurantId)))
                .collect(Collectors.toList());
    }
}

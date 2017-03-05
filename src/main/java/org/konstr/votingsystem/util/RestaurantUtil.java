package org.konstr.votingsystem.util;

import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.to.RestaurantTo;

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
}

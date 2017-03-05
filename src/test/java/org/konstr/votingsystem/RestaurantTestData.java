package org.konstr.votingsystem;

import org.konstr.votingsystem.matcher.ModelMatcher;
import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.to.RestaurantTo;

import java.util.Objects;

import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class RestaurantTestData {
    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = START_SEQ + 3;
    public static final int RESTAURANT_3_ID = START_SEQ + 4;

    public static final Restaurant RESTAURANT_1 = new Restaurant(
            RESTAURANT_1_ID, "Susi Vesla", "12 Russianova st.", "260-15-98"
    );
    public static final Restaurant RESTAURANT_2 = new Restaurant(
            RESTAURANT_2_ID, "Шоколадница", "ул. Брестская 22", "287-26-77"
    );
    public static final Restaurant RESTAURANT_3 = new Restaurant(
            RESTAURANT_3_ID, "Chaihana", "154 Nezavisimosti Avenue", "+375 (29) 245-32-45"
    );

    public static final ModelMatcher<Restaurant> MATCHER = ModelMatcher.of(Restaurant.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getAddress(), actual.getAddress())
                            && Objects.equals(expected.getPhoneNumber(), actual.getPhoneNumber())
                    )
    );

    public static final ModelMatcher<RestaurantTo> MATCHER_TO = ModelMatcher.of(RestaurantTo.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getAddress(), actual.getAddress())
                            && Objects.equals(expected.getPhoneNumber(), actual.getPhoneNumber())
                            && Objects.equals(expected.isSelected(), actual.isSelected())
                    )
    );
}

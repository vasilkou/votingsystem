package org.konstr.votingsystem;

import org.konstr.votingsystem.matcher.ModelMatcher;
import org.konstr.votingsystem.model.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
public class DishTestData {
    public static final int DISH_1_R1_ID = START_SEQ + 5;
    public static final int DISH_2_R1_ID = START_SEQ + 6;
    public static final int DISH_3_R2_ID = START_SEQ + 7;
    public static final int DISH_4_R2_ID = START_SEQ + 8;
    public static final int DISH_5_R2_ID = START_SEQ + 9;
    public static final int DISH_6_R3_ID = START_SEQ + 10;
    public static final int DISH_7_R3_ID = START_SEQ + 11;

    public static final Dish DISH_1_R1 = new Dish(DISH_1_R1_ID, "Sushi", 12.25f);
    public static final Dish DISH_2_R1 = new Dish(DISH_2_R1_ID, "Sashimi", 25.00f);
    public static final Dish DISH_3_R2 = new Dish(DISH_3_R2_ID, "Кофе", 1.50f);
    public static final Dish DISH_4_R2 = new Dish(DISH_4_R2_ID, "Горячий шоколад", 1.50f);
    public static final Dish DISH_5_R2 = new Dish(DISH_5_R2_ID, "Хлеб", 0.25f);
    public static final Dish DISH_6_R3 = new Dish(DISH_6_R3_ID, "Kalyan", 32.50f);
    public static final Dish DISH_7_R3 = new Dish(DISH_7_R3_ID, "Pahlava", 15.10f);

    public static final List<Dish> DISHES_1 = Arrays.asList(DISH_2_R1, DISH_1_R1);
    public static final List<Dish> DISHES_2 = Arrays.asList(DISH_4_R2, DISH_3_R2, DISH_5_R2);
    public static final List<Dish> DISHES_3 = Arrays.asList(DISH_6_R3, DISH_7_R3);

    public static final ModelMatcher<Dish> MATCHER = ModelMatcher.of(Dish.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getPrice(), actual.getPrice())
                    )
    );
}

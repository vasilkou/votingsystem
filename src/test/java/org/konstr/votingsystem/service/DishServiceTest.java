package org.konstr.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konstr.votingsystem.RestaurantTestData;
import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.konstr.votingsystem.DishTestData.*;
import static org.konstr.votingsystem.TestUtil.validateRootCause;

/**
 * Created by Yury Vasilkou
 * Date: 05-Mar-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private RestaurantService restaurantService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        Dish dish = service.get(DISH_1_R1_ID);
        MATCHER.assertEquals(DISH_1_R1, dish);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetMenu() throws Exception {
        List<Dish> menu = service.getMenu(RestaurantTestData.RESTAURANT_1_ID);
        MATCHER.assertCollectionEquals(DISHES_1, menu);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMenuNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testSave() throws Exception {
        Dish newDish = new Dish(null, "new dish", 33.33f);
        Dish created = service.save(newDish, RestaurantTestData.RESTAURANT_1_ID);
        newDish.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newDish, DISH_2_R1, DISH_1_R1), service.getMenu(RestaurantTestData.RESTAURANT_1_ID));
    }

    @Test
    public void testSaveMenu() throws Exception {
        Restaurant created = restaurantService.save(new Restaurant(null, "New Rest", "22 Grate st.", "222-24-33-121"));
        List<Dish> newMenu = Arrays.asList(
                new Dish(null, "one", 22.22f),
                new Dish(null, "two", 33.33f),
                new Dish(null, "z_three", 44.44f)
        );
        List<Dish> createdMenu = service.saveMenu(newMenu, created.getId());
        newMenu.forEach(dish1 -> createdMenu.stream()
                        .filter(dish2 -> dish1.getName().equals(dish2.getName()))
                        .findFirst()
                        .ifPresent(dish -> dish1.setId(dish.getId()))
        );
        MATCHER.assertCollectionEquals(newMenu, service.getMenu(created.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(DISH_1_R1_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(DISH_2_R1), service.getMenu(RestaurantTestData.RESTAURANT_1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testDeleteMenu() throws Exception {
        service.deleteMenu(RestaurantTestData.RESTAURANT_1_ID);
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getMenu(RestaurantTestData.RESTAURANT_1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDeleteMenu() throws Exception {
        service.delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH_1_R1);
        updated.setName("updated name");
        updated.setPrice(666.66f);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(DISH_1_R1_ID));
    }

    @Test
    public void testUpdateMenu() throws Exception {
        List<Dish> updated = DISHES_1;
        updated.get(0).setName("name 1");
        updated.get(1).setName("name 2");
        updated.get(2).setName("name 3");
        service.updateMenu(updated, RestaurantTestData.RESTAURANT_1_ID);
        MATCHER.assertCollectionEquals(updated, service.getMenu(RestaurantTestData.RESTAURANT_1_ID));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new Dish(null, " ", 33.33f), RestaurantTestData.RESTAURANT_1_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Dish(null, "name", -33.33f), RestaurantTestData.RESTAURANT_1_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Dish(null, "name", 1_000_000.01f), RestaurantTestData.RESTAURANT_1_ID), ConstraintViolationException.class);
    }
}
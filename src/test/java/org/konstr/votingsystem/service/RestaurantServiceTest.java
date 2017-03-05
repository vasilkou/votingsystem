package org.konstr.votingsystem.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konstr.votingsystem.DishTestData;
import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.to.RestaurantTo;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.konstr.votingsystem.RestaurantTestData.*;
import static org.konstr.votingsystem.TestUtil.validateRootCause;
import static org.konstr.votingsystem.util.RestaurantUtil.asTo;

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
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT_1_ID);
        MATCHER.assertEquals(RESTAURANT_1, restaurant);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByName() throws Exception {
        Restaurant restaurant = service.getByName("Susi Vesla");
        MATCHER.assertEquals(RESTAURANT_1, restaurant);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByNameNotFound() throws Exception {
        service.getByName("wrong name");
    }

    @Test
    public void testGetAll() throws Exception {
        List<RestaurantTo> all = service.getAll();
        MATCHER_TO.assertCollectionEquals(
                Arrays.asList(asTo(RESTAURANT_3), asTo(RESTAURANT_1, true), asTo(RESTAURANT_2)),
                all
        );

        List<Dish> menu1 = all.get(1).getMenu();
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.DISHES_1, menu1);

        List<Dish> menu2 = all.get(2).getMenu();
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.DISHES_2, menu2);

        List<Dish> menu3 = all.get(0).getMenu();
        DishTestData.MATCHER.assertCollectionEquals(DishTestData.DISHES_3, menu3);
    }

    @Test
    public void testSave() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New Rest", "22 Grate st.", "222-24-33-121");
        Restaurant created = service.save(newRestaurant);
        newRestaurant.setId(created.getId());
        MATCHER_TO.assertCollectionEquals(
                Arrays.asList(asTo(RESTAURANT_3), asTo(created), asTo(RESTAURANT_1, true), asTo(RESTAURANT_2)),
                service.getAll()
        );
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(RESTAURANT_1_ID);
        MATCHER_TO.assertCollectionEquals(Arrays.asList(asTo(RESTAURANT_3), asTo(RESTAURANT_2)), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Обновленный ресторан");
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(RESTAURANT_1_ID));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new Restaurant(null, " ", "133 address st", "+375-29-698-998-999")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Restaurant(null, "some name", " ", "+375-29-698-998-999")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Restaurant(null, "some name", "133 address st", "")), ConstraintViolationException.class);
    }
}

package org.konstr.votingsystem.web.dish;

import org.junit.Test;
import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.service.DishService;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.konstr.votingsystem.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.konstr.votingsystem.DishTestData.*;
import static org.konstr.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.ADMIN;
import static org.konstr.votingsystem.UserTestData.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 18-Mar-17.
 */
public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String URL = AdminDishController.URL + "/";

    @Autowired
    private DishService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(DISH_1_R1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID + "/dishes/1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetMenu() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(DISHES_1));
    }

    @Transactional
    @Test
    public void testCreate() throws Exception {
        Dish expected = new Dish(null, "new dish", 33.33f);
        ResultActions action = mockMvc.perform(post(URL + RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Dish returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, DISH_2_R1, DISH_1_R1), service.getMenu(RESTAURANT_1_ID));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Dish expected = new Dish(null, " ", -33.33f);
        mockMvc.perform(post(URL + RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateWithId() throws Exception {
        Dish expected = new Dish(1, "new dish", 33.33f);
        mockMvc.perform(post(URL + RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreateNotFound() throws Exception {
        Dish expected = new Dish(null, "new dish", 33.33f);
        mockMvc.perform(post(URL + "1/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Transactional
    @Test
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH_1_R1);
        updated.setName("updated name");
        updated.setPrice(666.66f);
        mockMvc.perform(put(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(DISH_1_R1_ID, RESTAURANT_1_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Dish updated = new Dish(DISH_1_R1);
        updated.setName(" ");
        updated.setPrice(-666.66f);
        mockMvc.perform(put(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateWrongId() throws Exception {
        Dish updated = new Dish(DISH_1_R1);
        updated.setName("updated name");
        mockMvc.perform(put(URL + RESTAURANT_1_ID + "/dishes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateWrongRestaurantId() throws Exception {
        Dish updated = new Dish(DISH_1_R1);
        updated.setName("updated name");
        mockMvc.perform(put(URL + "1/dishes/" + DISH_1_R1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Transactional
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + RESTAURANT_1_ID + "/dishes/" + DISH_1_R1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.singletonList(DISH_2_R1), service.getMenu(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + RESTAURANT_1_ID + "/dishes/1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Transactional
    @Test
    public void testDeleteMenu() throws Exception {
        mockMvc.perform(delete(URL + RESTAURANT_1_ID + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getMenu(RESTAURANT_1_ID));
    }

    @Test
    public void testDeleteMenuNotFound() throws Exception {
        mockMvc.perform(delete(URL + "1/dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
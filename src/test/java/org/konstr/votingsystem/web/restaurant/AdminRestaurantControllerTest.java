package org.konstr.votingsystem.web.restaurant;

import org.junit.Test;
import org.konstr.votingsystem.model.Restaurant;
import org.konstr.votingsystem.service.RestaurantService;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.konstr.votingsystem.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.konstr.votingsystem.RestaurantTestData.*;
import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.ADMIN;
import static org.konstr.votingsystem.UserTestData.USER;
import static org.konstr.votingsystem.UserTestData.USER_ID;
import static org.konstr.votingsystem.util.RestaurantUtil.asTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 18-Mar-17.
 */
public class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String URL = AdminRestaurantController.URL + "/";

    @Autowired
    private RestaurantService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(RESTAURANT_1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(URL + RESTAURANT_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetByName() throws Exception {
        mockMvc.perform(get(URL + "with?name=" + RESTAURANT_1.getName())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(RESTAURANT_1));
    }

    @Transactional
    @Test
    public void testCreate() throws Exception {
        Restaurant expected = new Restaurant(null, "New Rest", "22 Grate st.", "222-24-33-121");
        ResultActions action = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Restaurant returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER_TO.assertCollectionEquals(
                Arrays.asList(asTo(RESTAURANT_3), asTo(returned), asTo(RESTAURANT_1, true), asTo(RESTAURANT_2)),
                service.getAll(USER_ID)
        );
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Restaurant expected = new Restaurant(null, "", null, "222-24-33-121");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        Restaurant expected = new Restaurant(null, "Шоколадница", "ул. Брестская 22", "222-24");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateWithId() throws Exception {
        Restaurant expected = new Restaurant(1, "New Rest", "22 Grate st.", "222-24-33-121");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Transactional
    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Обновленный ресторан");
        mockMvc.perform(put(URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(RESTAURANT_1_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("");
        updated.setAddress(null);
        mockMvc.perform(put(URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateEmailDuplicate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Шоколадница");
        updated.setAddress("ул. Брестская 22");
        mockMvc.perform(put(URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void testUpdateWrongId() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("Обновленный ресторан");
        mockMvc.perform(put(URL + RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Transactional
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER_TO.assertCollectionEquals(Arrays.asList(asTo(RESTAURANT_3), asTo(RESTAURANT_2)), service.getAll(USER_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
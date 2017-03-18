package org.konstr.votingsystem.web.user;

import org.junit.Before;
import org.junit.Test;
import org.konstr.votingsystem.TestUtil;
import org.konstr.votingsystem.model.Role;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.repository.JpaUtil;
import org.konstr.votingsystem.service.UserService;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.konstr.votingsystem.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 17-Mar-17.
 */
public class AdminUserControllerTest extends AbstractControllerTest {

    private static final String URL = AdminUserController.URL + "/";

    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        userService.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER));
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
        mockMvc.perform(get(URL + USER_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(URL + USER_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(USER_1, USER_2, ADMIN, USER)));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(URL + "with?email=" + USER.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(USER));
    }

    @Transactional
    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        User returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_1, USER_2, ADMIN, expected, USER), userService.getAll());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        User expected = new User(null, null, "", "new", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateEmailDuplicate() throws Exception {
        User expected = new User(null, "New", "user@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCreateWithId() throws Exception {
        User expected = new User(1, "New", "user@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Transactional
    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userService.get(USER_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(USER);
        updated.setName("");
        updated.setPassword("");
        mockMvc.perform(put(URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateEmailDuplicate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(put(URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void testUpdateWrongId() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        mockMvc.perform(put(URL + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Transactional
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(USER_1, USER_2, ADMIN), userService.getAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testEnable() throws Exception {
        User expected = new User(USER);
        expected.setEnabled(false);

        mockMvc.perform(patch(URL + USER_ID + "?enabled=false")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(expected, userService.get(USER_ID));

        mockMvc.perform(patch(URL + USER_ID + "?enabled=true")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(USER, userService.get(USER_ID));
    }
}
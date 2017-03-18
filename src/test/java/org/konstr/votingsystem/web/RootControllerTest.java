package org.konstr.votingsystem.web;

import org.junit.Before;
import org.junit.Test;
import org.konstr.votingsystem.model.Role;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.repository.JpaUtil;
import org.konstr.votingsystem.service.UserService;
import org.konstr.votingsystem.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.*;
import static org.konstr.votingsystem.util.UserUtil.asTo;
import static org.konstr.votingsystem.web.RootController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 17-Mar-17.
 */
public class RootControllerTest extends AbstractControllerTest {

    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        userService.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Transactional
    @Test
    public void testRegister() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isOk());

        User returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_1, USER_2, ADMIN, expected, USER), userService.getAll());
    }

    @Test
    public void testRegisterInvalid() throws Exception {
        User expected = new User(null, null, "", "new", Role.ROLE_USER);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testRegisterEmailDuplicate() throws Exception {
        User expected = new User(null, "New", "user@yandex.ru", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void testRegisterWithId() throws Exception {
        User expected = new User(1, "New", "user@yandex.ru", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testRegisterAuth() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
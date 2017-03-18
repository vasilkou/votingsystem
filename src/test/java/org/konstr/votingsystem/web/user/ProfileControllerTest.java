package org.konstr.votingsystem.web.user;

import org.junit.Before;
import org.junit.Test;
import org.konstr.votingsystem.TestUtil;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.repository.JpaUtil;
import org.konstr.votingsystem.service.UserService;
import org.konstr.votingsystem.to.UserTo;
import org.konstr.votingsystem.util.UserUtil;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.konstr.votingsystem.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.*;
import static org.konstr.votingsystem.util.UserUtil.asTo;
import static org.konstr.votingsystem.web.user.ProfileController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 17-Mar-17.
 */
public class ProfileControllerTest extends AbstractControllerTest {

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
    public void testGetTo() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentMatcher(asTo(USER))));
    }

    @Test
    public void testGetToUnauth() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(USER_1, USER_2, ADMIN), userService.getAll());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(UserUtil.updateFromTo(new User(USER), updatedTo), userService.getByEmail("newemail@ya.ru"));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null);

        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdateWrongId() throws Exception {
        UserTo updatedTo = new UserTo(ADMIN_ID, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdateEmailDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "admin@gmail.com", "newPassword");

        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isConflict());
    }
}

package org.konstr.votingsystem.web.restaurant;

import org.junit.Test;
import org.konstr.votingsystem.TestUtil;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.springframework.http.MediaType;

import static org.konstr.votingsystem.RestaurantTestData.*;
import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.USER;
import static org.konstr.votingsystem.util.RestaurantUtil.asTo;
import static org.konstr.votingsystem.web.restaurant.RestaurantController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 18-Mar-17.
 */
public class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_TO.contentListMatcher(asTo(RESTAURANT_3), asTo(RESTAURANT_1, true), asTo(RESTAURANT_2))));
    }

    @Test
    public void testGetAllUnauth() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }
}
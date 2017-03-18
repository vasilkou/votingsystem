package org.konstr.votingsystem.web.vote;

import org.junit.Test;
import org.konstr.votingsystem.TestUtil;
import org.konstr.votingsystem.service.VoteService;
import org.konstr.votingsystem.util.VoteUtil;
import org.konstr.votingsystem.web.AbstractControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;

import static org.konstr.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static org.konstr.votingsystem.RestaurantTestData.RESTAURANT_2_ID;
import static org.konstr.votingsystem.TestUtil.userHttpBasic;
import static org.konstr.votingsystem.UserTestData.USER;
import static org.konstr.votingsystem.UserTestData.USER_2;
import static org.konstr.votingsystem.VoteTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yury Vasilkou
 * Date: 18-Mar-17.
 */
public class VoteControllerTest extends AbstractControllerTest {

    private static final String URL = VoteController.URL + "/";

    @Autowired
    private VoteService service;

    @Test
    public void testGetCurrent() throws Exception {
        mockMvc.perform(get(URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(CURRENT_RESULTS));
    }

    @Test
    public void testGetCurrentUnauth() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(URL + "/with?date=" + LocalDate.now())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(CURRENT_RESULTS));
    }

    @Test
    public void testGetByWrongDate() throws Exception {
        mockMvc.perform(get(URL + "/with?date=" + LocalDate.of(2016, Month.MAY, 22))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(Collections.emptyList()));
    }

    @Transactional
    @Test
    public void testVoteAndChangeBefore() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().plusMinutes(1));

        mockMvc.perform(put(URL + "restaurants/" + RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_NEW_VOTE, service.getCurrentResults());

        mockMvc.perform(put(URL + "restaurants/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_UPDATE_VOTE, service.getCurrentResults());
    }

    @Transactional
    @Test
    public void testVoteAfter() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().minusMinutes(1));

        mockMvc.perform(put(URL + "restaurants/" + RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(CURRENT_RESULTS_AFTER_NEW_VOTE, service.getCurrentResults());
    }

    @Transactional
    @Test
    public void testVoteChangeForbiddenAfter() throws Exception {
        // change the time when users can't change their vote
        TestUtil.setFinalStatic(VoteUtil.class.getField("END_OF_REVOTING"), LocalTime.now().minusMinutes(1));

        mockMvc.perform(put(URL + "restaurants/" + RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isOk());

        mockMvc.perform(put(URL + "restaurants/" + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_2)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
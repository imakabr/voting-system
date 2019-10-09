package ru.imakabr.votingsystem.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.VoteTestData.*;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.web.vote.VoteRestController.REST_URL;

public class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    @Test
    void testCreate() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        Vote expected = new Vote(returned.getId(), USER, TOKYO_CITY, LocalDate.now());

        assertMatch(returned, expected);
        assertMatch(returned.getRestaurant(), expected.getRestaurant());
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());
        Vote returned = readFromJson(action, Vote.class);
        int id = returned.getId();

        Restaurant updated = new Restaurant(KETCH_UP);
        mockMvc.perform(put(REST_URL + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        Vote expected = new Vote(id, USER, KETCH_UP, LocalDate.now());

        assertMatch(voteService.get(id, USER_ID), expected);
    }

    @Test
    void testDelete() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());
        Vote returned = readFromJson(action, Vote.class);
        int id = returned.getId();

        mockMvc.perform(delete(REST_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(voteService.getAllVotesByUserId(USER_ID), VOTE_FROM_USER_FOR_KETCHUP_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_FROM_USER_FOR_KETCHUP_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09));
    }
}

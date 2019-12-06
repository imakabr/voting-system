package ru.imakabr.votingsystem.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;
import ru.imakabr.votingsystem.web.restaurant.AdminRestaurantRestController;
import ru.imakabr.votingsystem.web.restaurant.RestaurantRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.TestUtil.userHttpBasic;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.VoteTestData.*;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.util.ValidationUtil.timeRestriction;
import static ru.imakabr.votingsystem.util.exception.ErrorType.*;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_VOTE;
import static ru.imakabr.votingsystem.web.vote.VoteRestController.REST_URL;

public class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(USER)));

        if (checkTime()) {
            action.andDo(print()).andExpect(status().isCreated());

            Vote returned = readFromJson(action, Vote.class);
            Vote expected = new Vote(returned.getId(), USER, TOKYO_CITY, LocalDate.now());

            assertMatch(returned, expected);
            assertMatch(returned.getRestaurant(), expected.getRestaurant());
        } else {
            action.andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(errorType(VOTING_ERROR));
        }
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(USER)));
        if (checkTime()) {
            ResultActions actionDuplicate = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(restaurant))
                    .with(userHttpBasic(USER)))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(errorType(VALIDATION_ERROR))
                    .andExpect(detailMessage(EXCEPTION_DUPLICATE_VOTE));

        } else {
            action.andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(errorType(VOTING_ERROR));
        }
    }

    @Test
    void update() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(USER)));
        if (checkTime()) {
            action.andExpect(status().isCreated());
            Vote returned = readFromJson(action, Vote.class);
            int id = returned.getId();
            Restaurant updated = new Restaurant(KETCH_UP);
            mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated))
                    .with(userHttpBasic(USER)))
                    .andExpect(status().isNoContent());
            Vote expected = new Vote(id, USER, KETCH_UP, LocalDate.now());
            assertMatch(voteService.get(id, USER_ID), expected);
        } else {
            action.andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(errorType(VOTING_ERROR));
        }
    }

    @Test
    void delete() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(USER)));
        if (checkTime()) {
            action.andExpect(status().isCreated());
            Vote returned = readFromJson(action, Vote.class);
            int id = returned.getId();
            ResultActions actionDelete = mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + id)
                    .with(userHttpBasic(USER)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            assertMatch(voteService.getAllVotesByUserId(USER_ID), VOTE_FROM_USER_FOR_KETCHUP_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09);
        } else {
            action.andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(errorType(VOTING_ERROR));
        }

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_FROM_USER_FOR_KETCHUP_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09));
    }

    @Test
    void getVoteByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/filter").param("date", "2019-09-20")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_FROM_USER_FOR_TOKYO_20_09))
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}

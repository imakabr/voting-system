package ru.imakabr.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.service.RestaurantService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.VoteTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.web.restaurant.AdminRestaurantRestController.REST_URL;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(KETCH_UP, KWAKINN, TOKYO_CITY, HACHAPURI_AND_WINE))
                .andDo(print());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + TOKYO_CITY_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
    }

    @Test
    void testCreate() throws Exception {
        Restaurant restaurant = new Restaurant(NEW_PUB);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        restaurant.setId(returned.getId());
        assertMatch(returned, restaurant);
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(KETCH_UP);
        updated.setName("FAKETCH_UP");
        mockMvc.perform(put(REST_URL + "/" + KETCH_UP_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.get(KETCH_UP_ID), updated);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + TOKYO_CITY_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), KETCH_UP, KWAKINN, HACHAPURI_AND_WINE);
    }

    @Test
    void getWithVotes() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + TOKYO_CITY_ID + "/votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_FROM_ADMIN_FOR_TOKYO_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09))
                .andDo(print());
    }

    @Test
    void getWithVotesByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + TOKYO_CITY_ID + "/users/filter").param("date", "2019-09-21"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new User[]{ADMIN}))
                .andDo(print());

    }
}

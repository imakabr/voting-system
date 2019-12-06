package ru.imakabr.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.item.AdminItemRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.ItemTestData.*;
import static ru.imakabr.votingsystem.TestUtil.userHttpBasic;
import static ru.imakabr.votingsystem.UserTestData.ADMIN;
import static ru.imakabr.votingsystem.UserTestData.USER;
import static ru.imakabr.votingsystem.VoteTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.imakabr.votingsystem.web.restaurant.RestaurantRestController.REST_URL;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/list"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/list")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(KETCH_UP, KWAKINN, TOKYO_CITY, HACHAPURI_AND_WINE))
                .andDo(print());
    }

    @Test
    void getRestaurantWithItemsToday() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID + "/today")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
        Restaurant returned = readFromJson(action, Restaurant.class);
        assertMatch(returned.getItems(), ITEMS_FOR_TOKYO_TODAY);
    }

    @Test
    void getRestaurantWithItemsByDate() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID + "/filter").param("date", "2019-09-20")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
        Restaurant returned = readFromJson(action, Restaurant.class);
        assertMatch(returned.getItems(), ITEMS_FOR_TOKYO_20_09);
    }

    @Test
    void getAllToday() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/today")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY, KETCH_UP))
                .andDo(print());
    }

    @Test
    void getAllByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/filter").param("date", "2019-09-20")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY, KETCH_UP))
                .andDo(print());
    }
}

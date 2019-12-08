package ru.imakabr.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.service.RestaurantService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.TestUtil.userHttpBasic;
import static ru.imakabr.votingsystem.VoteTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.imakabr.votingsystem.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT;
import static ru.imakabr.votingsystem.web.restaurant.AdminRestaurantRestController.REST_URL;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(KETCH_UP, KWAKINN, TOKYO_CITY, HACHAPURI_AND_WINE))
                .andDo(print());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
    }

    @Test
    void getInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + 100)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void create() throws Exception {
        Restaurant restaurant = new Restaurant(NEW_PUB);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        restaurant.setId(returned.getId());
        assertMatch(returned, restaurant);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Restaurant restaurant = new Restaurant(TOKYO_CITY);
        restaurant.setId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_RESTAURANT));
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant restaurant = new Restaurant(null, "a");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(KETCH_UP);
        updated.setName("FAKETCH_UP");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + KETCH_UP_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.get(KETCH_UP_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant updated = new Restaurant(KETCH_UP);
        updated.setName("a");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + KETCH_UP_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant updated = new Restaurant(KETCH_UP);
        updated.setName("TOKYO-CITY");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + KETCH_UP_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_RESTAURANT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + TOKYO_CITY_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), KETCH_UP, KWAKINN, HACHAPURI_AND_WINE);
    }

    @Test
    void deleteInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + 100)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));

    }

    @Test
    void getWithVotes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_FROM_ADMIN_FOR_TOKYO_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09))
                .andDo(print());
    }

    @Test
    void getWithVotesByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID + "/users/filter").param("date", "2019-09-21")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new User[]{ADMIN}))
                .andDo(print());

    }
}

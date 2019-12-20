package ru.imakabr.votingsystem.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Meal;
import ru.imakabr.votingsystem.service.MealService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.MealTestData.*;
import static ru.imakabr.votingsystem.MealTestData.assertMatch;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.TestUtil.userHttpBasic;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.imakabr.votingsystem.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_MEAL;
import static ru.imakabr.votingsystem.web.item.AdminMealRestController.REST_URL;

public class AdminMealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/meals/" + START_MEAL_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/meals/" + START_MEAL_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL_0))
                .andDo(print());
    }

    @Test
    void getInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/meals/" + 1000)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void create() throws Exception {
        Meal expected = new Meal(NEW_MEAL);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());
        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
    }

    @Test
    void createForbiddenAuth() throws Exception {
        Meal expected = new Meal(NEW_MEAL);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Meal updated = new Meal(MEAL_0);
        updated.setId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_MEAL));
    }

    @Test
    void update() throws Exception {
        Meal updated = new Meal(MEAL_0);
        updated.setName("wine");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/meals/" + START_MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(START_MEAL_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Meal updated = new Meal(MEAL_0);
        updated.setName(null);

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/meals/" + START_MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Meal updated = new Meal(MEAL_0);
        updated.setDate(LocalDate.of(2019, 9, 21));
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/meals/" + START_MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_MEAL));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/meals/" + START_MEAL_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(), MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7);
    }

    @Test
    void deleteInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/meals/" + 10000)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void getAllByRestaurantId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID  + "/meals")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
    }
}

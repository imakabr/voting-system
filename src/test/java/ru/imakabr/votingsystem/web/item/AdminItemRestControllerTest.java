package ru.imakabr.votingsystem.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.service.ItemService;
import ru.imakabr.votingsystem.to.UserTo;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;
import ru.imakabr.votingsystem.web.restaurant.AdminRestaurantRestController;
import ru.imakabr.votingsystem.web.user.ProfileRestController;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.ItemTestData.*;
import static ru.imakabr.votingsystem.ItemTestData.assertMatch;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.TestUtil.userHttpBasic;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static ru.imakabr.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_ITEM;
import static ru.imakabr.votingsystem.web.item.AdminItemRestController.REST_URL;

public class AdminItemRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ItemService itemService;

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/items/" + START_ITEM_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/items/" + START_ITEM_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ITEM0))
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        Item expected = new Item(NEW_ITEM);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());
        Item returned = readFromJson(action, Item.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
    }

    @Test
    void createForbiddenAuth() throws Exception {
        Item expected = new Item(NEW_ITEM);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Item updated = new Item(ITEM0);
        updated.setId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/" + TOKYO_CITY_ID + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_ITEM));
    }

    @Test
    void update() throws Exception {
        Item updated = new Item(ITEM0);
        updated.setName("wine");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/items/" + START_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(itemService.get(START_ITEM_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Item updated = new Item(ITEM0);
        updated.setName(null);

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/items/" + START_ITEM_ID)
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
        Item updated = new Item(ITEM0);
        updated.setDate(LocalDate.of(2019, 9, 21));
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + TOKYO_CITY_ID + "/items/" + START_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_ITEM));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/items/" + START_ITEM_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(itemService.getAll(), ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7);
    }

    @Test
    void getAllByRestaurantId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + TOKYO_CITY_ID  + "/items")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
    }
}

package ru.imakabr.votingsystem.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.service.ItemService;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;
import ru.imakabr.votingsystem.web.restaurant.AdminRestaurantRestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.ItemTestData.*;
import static ru.imakabr.votingsystem.ItemTestData.assertMatch;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.TestUtil.readFromJson;
import static ru.imakabr.votingsystem.web.item.AdminItemRestController.REST_URL;

public class AdminItemRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ItemService itemService;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + START_ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ITEM0))
                .andDo(print());
    }

    @Test
    void testCreate() throws Exception {
        Item expected = new Item(NEW_ITEM);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Item returned = readFromJson(action, Item.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Item updated = new Item(ITEM0);
        updated.setName("wine");
        mockMvc.perform(put(REST_URL + "/" + START_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(itemService.get(START_ITEM_ID), updated);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + START_ITEM_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(itemService.getAll(), ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7);
    }

    @Test
    void getAllByRestaurantId() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants/" + TOKYO_CITY_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TOKYO_CITY))
                .andDo(print());
    }


}

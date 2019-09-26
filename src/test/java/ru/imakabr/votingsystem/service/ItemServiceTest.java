package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.imakabr.votingsystem.ItemTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;

public class ItemServiceTest extends AbstractServiceTest {

    @Autowired
    protected ItemService itemService;

    @Test
    void create() throws Exception {
        Item newItem = new Item(null, RestaurantTestData.TOKYO_CITY, "vodka", 700, LocalDateTime.of(2019, 9, 22, 10, 0));
        Item created = itemService.create(newItem);
        newItem.setId(created.getId());
        assertMatch(created, newItem);
    }

    @Test
    void update() throws Exception {
        Item updated = new Item(null,  RestaurantTestData.TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0));
        updated.setPrice(200);
        itemService.update(updated);
        Integer id = updated.getId();
        assertMatch(new Item(id,  RestaurantTestData.TOKYO_CITY, "beer", 200, LocalDateTime.of(2019, 9, 21, 10, 0)), updated);
    }

    @Test
    void delete() throws Exception {
        itemService.delete(ITEM1.getId());
        assertMatch(itemService.getAll(), ITEM2, ITEM3, ITEM4, ITEM5);
    }

    @Test
    void getAll() throws Exception {
        List<Item> all = itemService.getAll();
        assertMatch(all, ALL_ITEMS);
    }

}

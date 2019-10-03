package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.model.Item;
import java.time.LocalDate;
import java.util.List;

import static ru.imakabr.votingsystem.ItemTestData.*;

public class ItemServiceTest extends AbstractServiceTest {

    @Autowired
    protected ItemService itemService;

    @Test
    void create() throws Exception {
        Item newItem = new Item(null, RestaurantTestData.TOKYO_CITY, "vodka", 700, LocalDate.of(2019, 9, 22));
        Item created = itemService.create(newItem);
        newItem.setId(created.getId());
        assertMatch(created, newItem);
    }

    @Test
    void update() throws Exception {
        Item updated = new Item(ITEM0);
        updated.setPrice(200);
        itemService.update(updated, updated.getId());
        Integer id = updated.getId();
        assertMatch(itemService.get(id), updated);
    }

    @Test
    void get() throws Exception {
        Item actual = itemService.get(ITEM0.getId());
        assertMatch(actual, ITEM0);
    }

    @Test
    void delete() throws Exception {
        itemService.delete(ITEM0.getId());
        assertMatch(itemService.getAll(), ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7);
    }

    @Test
    void getAll() throws Exception {
        List<Item> all = itemService.getAll();
        assertMatch(all, ALL_ITEMS);
    }

}

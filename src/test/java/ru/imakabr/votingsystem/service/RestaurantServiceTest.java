package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.UserTestData;
import ru.imakabr.votingsystem.VoteTestData;
import ru.imakabr.votingsystem.model.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void get() throws Exception {
        Restaurant actual = restaurantService.get(TOKYO_CITY_ID);
        assertMatch(actual, TOKYO_CITY);
    }

    @Test
    void create() throws Exception {
        Restaurant newRest = new Restaurant(null, "Rock Pub");
        Restaurant created = restaurantService.create(newRest);
        newRest.setId(created.getId());
        assertMatch(created, newRest);
        assertMatch(restaurantService.getAll(), KETCH_UP, KWAKINN, newRest, TOKYO_CITY, HACHAPURI_AND_WINE);
    }

    @Test
    void getWithVotes() throws Exception {
        Restaurant actual = restaurantService.getWithVotes(TOKYO_CITY_ID);
        Vote vote = actual.getVotes().get(0);
        UserTestData.assertMatch(vote.getUser(), UserTestData.USER);
        assertMatch(vote.getRestaurant(), TOKYO_CITY);
    }

    @Test
    void getWithItems() throws Exception {
        Restaurant actual = restaurantService.getWithItems(TOKYO_CITY_ID);
        assertMatch(actual, TOKYO_CITY);
        ItemTestData.assertMatch(actual.getItems(), ItemTestData.ITEMS_FOR_TOKYO_ALL);
    }

    @Test
    void getAllByDate() {
        List<Restaurant> actual = restaurantService.getAllByDate(LocalDateTime.of(2019, 9, 20, 10, 0));
        ItemTestData.assertMatch(actual.get(0).getItems(), ItemTestData.ITEMS_FOR_TOKYO_20_09);
        ItemTestData.assertMatch(actual.get(1).getItems(), ItemTestData.ITEMS_FOR_KETCHUP_20_09);
        assertMatch(actual, TOKYO_CITY, KETCH_UP, HACHAPURI_AND_WINE, KWAKINN);
    }

    @Test
    void getAllWithItemsByDate() {
        List<Restaurant> actual = restaurantService.getAllWithItemsByDate(LocalDateTime.of(2019, 9, 20, 10, 0));
        ItemTestData.assertMatch(actual.get(0).getItems(), ItemTestData.ITEMS_FOR_TOKYO_20_09);
        ItemTestData.assertMatch(actual.get(1).getItems(), ItemTestData.ITEMS_FOR_KETCHUP_20_09);
        assertMatch(actual, TOKYO_CITY, KETCH_UP);
    }

    @Test
    void getAll() {
        List<Restaurant> actual = restaurantService.getAll();
        assertMatch(actual,  RESTAURANTS);
    }

}

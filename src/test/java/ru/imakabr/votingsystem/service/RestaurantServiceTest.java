package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.UserTestData;
import ru.imakabr.votingsystem.VoteTestData;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

import static ru.imakabr.votingsystem.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void get() throws Exception {
        Restaurant actual = restaurantService.get(TOKYO_CITY_ID);
        Restaurant expected = new Restaurant(TOKYO_CITY);
        expected.setItems(ItemTestData.ITEMS_FOR_TOKYO_ALL);
        assertMatch(actual, expected);
    }

    @Test
    void getWithVotes() throws Exception {
        Restaurant actual = restaurantService.getWithVotes(TOKYO_CITY_ID);
        Vote vote = actual.getVotes().get(0);
        UserTestData.assertMatch(vote.getUser(), UserTestData.USER);
        assertMatch(vote.getRestaurant(), TOKYO_CITY);
    }

//    @Test
//    void getByDate() {
//        Restaurant actual = restaurantService.getByDate(TOKYO_CITY_ID, LocalDateTime.of(2019, 9, 21, 10, 0));
//        Restaurant expected = new Restaurant(TOKYO_CITY);
//        expected.setItems(ItemTestData.ITEMS_FOR_TOKYO_21_09);
//        assertMatch(actual, expected);
//    }

    @Test
    void getAllByDate() {
        List<Restaurant> actual = restaurantService.getAllByDate(LocalDateTime.of(2019, 9, 20, 10, 0));
        ItemTestData.assertMatch(actual.get(0).getItems(), ItemTestData.ITEMS_FOR_TOKYO_20_09);
        ItemTestData.assertMatch(actual.get(1).getItems(), ItemTestData.ITEMS_FOR_KETCHUP_20_09);
        assertMatch(actual, TOKYO_CITY, KETCH_UP, HACHAPURI_AND_WINE, KWAKINN);
    }

    @Test
    void getAll() {
        List<Restaurant> actual = restaurantService.getAll();
        assertMatch(actual, RESTAURANTS);
    }

}

package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.model.Restaurant;

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
    void getByDate() {
        Restaurant actual = restaurantService.getByDate(TOKYO_CITY_ID, LocalDateTime.of(2019, 9, 21, 10, 0));
        Restaurant expected = new Restaurant(TOKYO_CITY);
        expected.setItems(ItemTestData.ITEMS_FOR_TOKYO_21_09);
        assertMatch(actual, expected);
    }

    @Test
    void getAllByDate() {
        List<Restaurant> actual = restaurantService.getAllByDate(LocalDateTime.of(2019, 9, 21, 10, 0));
        Restaurant tokyo = new Restaurant(TOKYO_CITY);
        tokyo.setItems(ItemTestData.ITEMS_FOR_TOKYO_20_09);
        assertMatch(actual, tokyo, KETCH_UP, HACHAPURI_AND_WINE, KWAKINN);
    }

    @Test
    void getAll() {
        List<Restaurant> actual = restaurantService.getAll();
        assertMatch(actual, RESTAURANTS);
    }

}

package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.model.*;
import ru.imakabr.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.imakabr.votingsystem.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void get() throws Exception {
        Restaurant actual = restaurantService.get(TOKYO_CITY_ID);
        assertMatch(actual, TOKYO_CITY);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.get(1));
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
    void duplicateCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                restaurantService.create(new Restaurant(null, "KWAKINN")));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(TOKYO_CITY);
        updated.setName("TOKYO");
        restaurantService.update(updated, updated.getId());
        Integer id = updated.getId();
        assertMatch(restaurantService.get(id), updated);
    }

    @Test
    void delete() throws Exception {
        restaurantService.delete(TOKYO_CITY_ID);
        assertMatch(restaurantService.getAll(), KETCH_UP, KWAKINN, HACHAPURI_AND_WINE);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(1111));
    }

    @Test
    void getWithItems() throws Exception {
        Restaurant actual = restaurantService.getWithItems(TOKYO_CITY_ID);
        assertMatch(actual, TOKYO_CITY);
        ItemTestData.assertMatch(actual.getItems(), ItemTestData.ITEMS_FOR_TOKYO_ALL);
    }

    @Test
    void getAllWithItemsByDate() {
        List<Restaurant> actual = restaurantService.getAllWithItemsByDate(LocalDate.of(2019, 9, 20));
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

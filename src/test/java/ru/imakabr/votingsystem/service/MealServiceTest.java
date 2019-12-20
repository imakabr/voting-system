package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.model.Meal;
import ru.imakabr.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.imakabr.votingsystem.MealTestData.*;
import static ru.imakabr.votingsystem.RestaurantTestData.TOKYO_CITY;
import static ru.imakabr.votingsystem.RestaurantTestData.TOKYO_CITY_ID;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService mealService;

    @Test
    void create() throws Exception {
        Meal newMeal = new Meal(null, RestaurantTestData.TOKYO_CITY, "vodka", 700, LocalDate.of(2019, 9, 22));
        Meal created = mealService.create(newMeal, TOKYO_CITY_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
    }

    @Test
    void duplicateCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null, TOKYO_CITY, "wok", 200, LocalDate.of(2019, 9, 20)), TOKYO_CITY_ID));
    }

    @Test
    void update() throws Exception {
        Meal updated = new Meal(MEAL_0);
        updated.setPrice(200);
        mealService.update(updated, TOKYO_CITY_ID, updated.getId());
        Integer id = updated.getId();
        assertMatch(mealService.get(id), updated);
    }

    @Test
    void get() throws Exception {
        Meal actual = mealService.get(MEAL_0.getId());
        assertMatch(actual, MEAL_0);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                mealService.get(1));
    }

    @Test
    void delete() throws Exception {
        mealService.delete(MEAL_0.getId());
        assertMatch(mealService.getAll(), MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                mealService.delete(1111));
    }

    @Test
    void getAll() throws Exception {
        List<Meal> all = mealService.getAll();
        assertMatch(all, ALL_MEALS);
    }


}

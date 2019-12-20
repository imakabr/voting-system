package ru.imakabr.votingsystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.imakabr.votingsystem.model.Meal;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.imakabr.votingsystem.TestUtil.readFromJsonMvcResult;
import static ru.imakabr.votingsystem.TestUtil.readListFromJsonMvcResult;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.model.Meal.START_SEQ;

public class MealTestData {
    public static final int START_MEAL_ID = START_SEQ;

    public static final Meal MEAL_0 = new Meal(START_MEAL_ID, TOKYO_CITY, "beer", 150, LocalDate.of(2019, 9, 20));
    public static final Meal MEAL_1 = new Meal(START_MEAL_ID + 1, TOKYO_CITY, "wok", 200, LocalDate.of(2019, 9, 20));
    public static final Meal MEAL_2 = new Meal(START_MEAL_ID + 2, KETCH_UP, "beer", 300, LocalDate.of(2019, 9, 20));
    public static final Meal MEAL_3 = new Meal(START_MEAL_ID + 3, TOKYO_CITY, "beer", 150, LocalDate.of(2019, 9, 21));
    public static final Meal MEAL_4 = new Meal(START_MEAL_ID + 4, KETCH_UP, "salad", 200, LocalDate.of(2019, 9, 21));
    public static final Meal MEAL_5 = new Meal(START_MEAL_ID + 5, TOKYO_CITY, "salad", 200, LocalDate.now());
    public static final Meal MEAL_6 = new Meal(START_MEAL_ID + 6, TOKYO_CITY, "sushi", 350, LocalDate.now());
    public static final Meal MEAL_7 = new Meal(START_MEAL_ID + 7, KETCH_UP, "sushi", 420, LocalDate.now());
    public static final Meal NEW_MEAL = new Meal(null, KETCH_UP, "drink", 500, LocalDate.now());

    public static final List<Meal> ALL_MEALS = List.of(MEAL_0, MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7);

    public static final List<Meal> MEALS_FOR_TOKYO_ALL = List.of(
            MEAL_5,
            MEAL_6,
            MEAL_3,
            MEAL_0,
            MEAL_1
    );

    public static final List<Meal> MEALS_FOR_TOKYO_2009 = List.of(
            MEAL_0,
            MEAL_1
    );

    public static final List<Meal> MEALS_FOR_TOKYO_2109 = List.of(
            MEAL_3
    );

    public static final List<Meal> MEALS_FOR_KETCHUP_2009 = List.of(
            MEAL_2
    );

    public static final List<Meal> MEALS_FOR_TOKYO_TODAY = List.of(
            MEAL_5, MEAL_6
    );

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Meal... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Meal.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Meal expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Meal.class), expected);
    }

}

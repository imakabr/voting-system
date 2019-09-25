package ru.imakabr.votingsystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.imakabr.votingsystem.TestUtil.readFromJsonMvcResult;
import static ru.imakabr.votingsystem.TestUtil.readListFromJsonMvcResult;
import static ru.imakabr.votingsystem.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int TOKYO_CITY_ID = START_SEQ + 2;
    public static final int KETCH_UP_ID = START_SEQ + 3;
    public static final int HACHAPURI_AND_WINE_ID = START_SEQ + 4;
    public static final int KWAKINN_ID = START_SEQ + 5;

    public static final Restaurant TOKYO_CITY = new Restaurant(TOKYO_CITY_ID, "TOKYO-CITY");
    public static final Restaurant KETCH_UP = new Restaurant(KETCH_UP_ID, "KETCH-UP");
    public static final Restaurant HACHAPURI_AND_WINE = new Restaurant(HACHAPURI_AND_WINE_ID, "ХАЧАПУРИ И ВИНО");
    public static final Restaurant KWAKINN = new Restaurant(KWAKINN_ID, "KWAKINN");


    public static final List<Restaurant> RESTAURANTS = List.of(KETCH_UP, KWAKINN, TOKYO_CITY, HACHAPURI_AND_WINE);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "items", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("items", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }
}

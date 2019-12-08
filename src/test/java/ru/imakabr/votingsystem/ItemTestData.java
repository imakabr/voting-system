package ru.imakabr.votingsystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.imakabr.votingsystem.TestUtil.readFromJsonMvcResult;
import static ru.imakabr.votingsystem.TestUtil.readListFromJsonMvcResult;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.model.Item.START_SEQ;

public class ItemTestData {
    public static final int START_ITEM_ID = START_SEQ;

    public static final Item ITEM0 = new Item(START_ITEM_ID, TOKYO_CITY, "beer", 150, LocalDate.of(2019, 9, 20));
    public static final Item ITEM1 = new Item(START_ITEM_ID + 1, TOKYO_CITY, "wok", 200, LocalDate.of(2019, 9, 20));
    public static final Item ITEM2 = new Item(START_ITEM_ID + 2, KETCH_UP, "beer", 300, LocalDate.of(2019, 9, 20));
    public static final Item ITEM3 = new Item(START_ITEM_ID + 3, TOKYO_CITY, "beer", 150, LocalDate.of(2019, 9, 21));
    public static final Item ITEM4 = new Item(START_ITEM_ID + 4, KETCH_UP, "salad", 200, LocalDate.of(2019, 9, 21));
    public static final Item ITEM5 = new Item(START_ITEM_ID + 5, TOKYO_CITY, "salad", 200, LocalDate.now());
    public static final Item ITEM6 = new Item(START_ITEM_ID + 6, TOKYO_CITY, "sushi", 350, LocalDate.now());
    public static final Item ITEM7 = new Item(START_ITEM_ID + 7, KETCH_UP, "sushi", 420, LocalDate.now());
    public static final Item NEW_ITEM = new Item(null, KETCH_UP, "drink", 500, LocalDate.now());

    public static final List<Item> ALL_ITEMS = List.of(ITEM0, ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7);

    public static final List<Item> ITEMS_FOR_TOKYO_ALL = List.of(
            ITEM5,
            ITEM6,
            ITEM3,
            ITEM0,
            ITEM1
    );

    public static final List<Item> ITEMS_FOR_TOKYO_20_09 = List.of(
            ITEM0,
            ITEM1
    );

    public static final List<Item> ITEMS_FOR_TOKYO_21_09 = List.of(
            ITEM3
    );

    public static final List<Item> ITEMS_FOR_KETCHUP_20_09 = List.of(
            ITEM2
    );

    public static final List<Item> ITEMS_FOR_TOKYO_TODAY = List.of(
            ITEM5, ITEM6
    );

    public static void assertMatch(Item actual, Item expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Item> actual, Item... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Item> actual, Iterable<Item> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Item... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Item.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Item expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Item.class), expected);
    }

}

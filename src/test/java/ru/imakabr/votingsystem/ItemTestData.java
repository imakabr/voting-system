package ru.imakabr.votingsystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.imakabr.votingsystem.TestUtil.readFromJsonMvcResult;
import static ru.imakabr.votingsystem.TestUtil.readListFromJsonMvcResult;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.model.Item.START_SEQ;

public class ItemTestData {
    public static final int START_ITEM_ID = START_SEQ;

    public static final Item ITEM1 = new Item(START_ITEM_ID, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0));
    public static final Item ITEM2 = new Item(START_ITEM_ID+1, TOKYO_CITY, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0));
    public static final Item ITEM3 = new Item(START_ITEM_ID+2, KETCH_UP, "beer", 300, LocalDateTime.of(2019, 9, 20, 10, 0));
    public static final Item ITEM4 = new Item(START_ITEM_ID+3, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0));
    public static final Item ITEM5 = new Item(START_ITEM_ID+4, KETCH_UP, "salad", 200, LocalDateTime.of(2019, 9, 21, 10, 0));

//    public static final List<Item> ALL_ITEMS = List.of(
//            new Item(100006, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0)),
//            new Item(100007, TOKYO_CITY, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0)),
//            new Item(100008, KETCH_UP, "beer", 300, LocalDateTime.of(2019, 9, 20, 10, 0)),
//            new Item(100009, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0)),
//            new Item(100010, KETCH_UP, "salad", 200, LocalDateTime.of(2019, 9, 21, 10, 0))
//    );

    public static final List<Item> ALL_ITEMS = List.of(ITEM1, ITEM2, ITEM3, ITEM4, ITEM5);

    public static final List<Item> ITEMS_FOR_TOKYO_ALL = List.of(
            new Item(START_ITEM_ID+3, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0)), //9
            new Item(START_ITEM_ID, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0)), //6
            new Item(START_ITEM_ID+1, TOKYO_CITY, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0))  //7
    );

    public static final List<Item> ITEMS_FOR_TOKYO_20_09 = List.of(
            new Item(START_ITEM_ID, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0)), //6
            new Item(START_ITEM_ID+1, TOKYO_CITY, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0)) //7
    );

    public static final List<Item> ITEMS_FOR_TOKYO_21_09 = List.of(
            new Item(START_ITEM_ID+3, TOKYO_CITY, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0)) //9
    );

    public static final List<Item> ITEMS_FOR_KETCHUP_20_09 = List.of(
            new Item(START_ITEM_ID+2, KETCH_UP, "beer", 300, LocalDateTime.of(2019, 9, 20, 10, 0))  //8
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

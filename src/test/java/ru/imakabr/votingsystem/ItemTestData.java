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

public class ItemTestData {
    public static final int START_ITEM_ID = START_SEQ + 6;

    public static final Item BEER_20_09 = new Item(100006, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0));
    public static final Item BEER_21_09 = new Item(100006, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0));
    public static final Item WOK = new Item(100007, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0));
    public static final Item SALAD = new Item(100007, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0));


    public static final List<Item> ITEMS_FOR_TOKYO_ALL = List.of(
            new Item(100006, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0)),
            new Item(100007, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0)),
            new Item(100009, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0))
    );

    public static final List<Item> ITEMS_FOR_TOKYO_20_09 = List.of(
            new Item(100006, "beer", 150, LocalDateTime.of(2019, 9, 20, 10, 0)),
            new Item(100007, "wok", 200, LocalDateTime.of(2019, 9, 20, 10, 0))
    );

    public static final List<Item> ITEMS_FOR_TOKYO_21_09 = List.of(
            new Item(100009, "beer", 150, LocalDateTime.of(2019, 9, 21, 10, 0))
    );

    public static final List<Item> ITEMS_FOR_KETCHUP_20_09 = List.of(
            new Item(100008, "beer", 300, LocalDateTime.of(2019, 9, 20, 10, 0))
    );

    public static void assertMatch(Item actual, Item expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatch(Iterable<Item> actual, Item... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Item> actual, Iterable<Item> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Item... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Item.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Item expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Item.class), expected);
    }
}

package ru.imakabr.votingsystem;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.imakabr.votingsystem.TestUtil.readFromJsonMvcResult;
import static ru.imakabr.votingsystem.TestUtil.readListFromJsonMvcResult;
import static ru.imakabr.votingsystem.model.Vote.START_SEQ;
import static ru.imakabr.votingsystem.util.ValidationUtil.timeRestriction;

public class VoteTestData {

    public static final int VOTE_ID = START_SEQ;

    public static final Vote VOTE_FROM_USER_FOR_TOKYO_20_09 = new Vote(VOTE_ID, UserTestData.USER, RestaurantTestData.TOKYO_CITY, LocalDate.of(2019, 9, 20));
    public static final Vote VOTE_FROM_USER_FOR_KETCHUP_21_09 = new Vote(VOTE_ID+1, UserTestData.USER, RestaurantTestData.KETCH_UP, LocalDate.of(2019, 9, 21));
    public static final Vote VOTE_FROM_ADMIN_FOR_TOKYO_21_09 = new Vote(VOTE_ID+2, UserTestData.ADMIN, RestaurantTestData.TOKYO_CITY, LocalDate.of(2019, 9, 21));

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }

    public static boolean checkTime() {
        LocalTime now = LocalTime.now();
        return !timeRestriction.isBefore(now);
    }

}

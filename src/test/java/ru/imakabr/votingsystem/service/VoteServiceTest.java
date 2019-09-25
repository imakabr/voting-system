package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.UserTestData;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;

import java.util.List;

import static ru.imakabr.votingsystem.RestaurantTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void get() throws Exception {

    }

    @Test
    void getAllRestaurantsByUserId() {
        List<Restaurant> votes = voteService.getAllRestaurantsByUserId(UserTestData.USER_ID);
        RestaurantTestData.assertMatch(votes, TOKYO_CITY, KETCH_UP);
    }

    @Test
    void getAllUsersByRestaurantId() {
        List<User> users = voteService.getAllUsersByRestaurantId(TOKYO_CITY_ID);
        UserTestData.assertMatch(users, UserTestData.USER);
    }
}

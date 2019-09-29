package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.UserTestData;
import ru.imakabr.votingsystem.VoteTestData;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void create() throws Exception {
        Vote newVote = new Vote(null, UserTestData.USER, TOKYO_CITY, LocalDateTime.of(2019, 9, 22, 10, 0));
        Vote created = voteService.create(newVote);
        newVote.setId(created.getId());
        VoteTestData.assertMatch(created, newVote);
    }

    @Test
    void update() throws Exception {
        Vote updated = new Vote(VOTE_ID, UserTestData.USER, TOKYO_CITY, LocalDateTime.of(2019, 9, 20, 10, 0));
        updated.setRestaurant(KWAKINN);
        voteService.update(updated);
        assertMatch(voteService.getRestaurantByUserIdAndDateTime(USER_ID, LocalDateTime.of(2019, 9, 20, 10, 0)), KWAKINN);
    }

    @Test
    void getAllRestaurantsByUserId() {
        List<Restaurant> restaurants = voteService.getAllRestaurantsByUserId(UserTestData.USER_ID);
        RestaurantTestData.assertMatch(restaurants, TOKYO_CITY, KETCH_UP);
    }

    @Test
    void getAllVotesByUserId() {
        List<Vote> votes = voteService.getAllVotesByUserId(USER_ID);
        assertMatch(votes, VOTE_FROM_USER_FOR_TOKYO_20_09, VOTE_FROM_USER_FOR_KETCHUP_21_09);
        assertMatch(votes.get(0).getRestaurant(), TOKYO_CITY);
        assertMatch(votes.get(1).getRestaurant(), KETCH_UP);
    }

    @Test
    void getAllUsersByRestaurantId() {
        List<User> users = voteService.getAllUsersByRestaurantId(TOKYO_CITY_ID);
        UserTestData.assertMatch(users, UserTestData.USER, UserTestData.ADMIN);
    }

    @Test
    void getRestaurantByUserIdAndDateTime() {
        Restaurant restaurant = voteService.getRestaurantByUserIdAndDateTime(UserTestData.USER_ID, LocalDateTime.of(2019, 9, 20, 10, 0));
        RestaurantTestData.assertMatch(restaurant, TOKYO_CITY);
    }

    @Test
    void getAllUsersByRestaurantIdAndDateTime() {
        List<User> users = voteService.getAllUsersByRestaurantIdAndDateTime(TOKYO_CITY_ID, LocalDateTime.of(2019, 9, 20, 10, 0));
        UserTestData.assertMatch(users.get(0), UserTestData.USER);
    }
}

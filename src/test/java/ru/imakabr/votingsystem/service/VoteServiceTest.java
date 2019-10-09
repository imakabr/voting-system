package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.imakabr.votingsystem.RestaurantTestData;
import ru.imakabr.votingsystem.UserTestData;
import ru.imakabr.votingsystem.VoteTestData;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.imakabr.votingsystem.RestaurantTestData.*;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void create() throws Exception {
        Vote newVote = new Vote(null, UserTestData.USER, TOKYO_CITY, LocalDate.now());
        Vote created = voteService.create(TOKYO_CITY, USER_ID);
        newVote.setId(created.getId());
        VoteTestData.assertMatch(created, newVote);
    }

    @Test
    void duplicateCreate() throws Exception {
        voteService.create(TOKYO_CITY, USER_ID);
        assertThrows(DataAccessException.class, () ->
                voteService.create(KWAKINN, USER_ID));
    }

    @Test
    void delete() throws Exception {
        voteService.create(TOKYO_CITY, USER_ID);
        voteService.delete(VOTE_ID+3, USER_ID);
        List<Vote> votes = voteService.getAllVotesByUserId(USER_ID);
        assertMatch(votes.get(0).getRestaurant(), KETCH_UP);
        assertMatch(votes.get(1).getRestaurant(), TOKYO_CITY);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                voteService.delete(1111, USER_ID));
    }

    @Test
    void get() throws Exception {
        Vote vote = voteService.get(VOTE_ID, USER_ID);
        assertMatch(vote, VOTE_FROM_USER_FOR_TOKYO_20_09);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                voteService.get(1, USER_ID));
    }

//    @Test
//    void update() throws Exception {
//        Vote updated = new Vote(VOTE_ID, UserTestData.USER, KWAKINN, LocalDate.of(2019, 9, 20));
//        voteService.update(TOKYO_CITY, USER_ID, VOTE_ID);
//        assertMatch(voteService.getRestaurantByUserIdAndDate(USER_ID, LocalDate.of(2019, 9, 20)), KWAKINN);
//    }

    @Test
    void getAllVotesByUserId() {
        List<Vote> votes = voteService.getAllVotesByUserId(USER_ID);
        assertMatch(votes, VOTE_FROM_USER_FOR_KETCHUP_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09);
        assertMatch(votes.get(0).getRestaurant(), KETCH_UP);
        assertMatch(votes.get(1).getRestaurant(), TOKYO_CITY);
    }

    @Test
    void getAllVotesByRestaurantId() {
        List<Vote> votes = voteService.getAllVotesByRestaurantId(TOKYO_CITY_ID);
        assertMatch(votes, VOTE_FROM_ADMIN_FOR_TOKYO_21_09, VOTE_FROM_USER_FOR_TOKYO_20_09);
        assertMatch(votes.get(0).getUser(), ADMIN);
        assertMatch(votes.get(1).getUser(), USER);

    }

    @Test
    void getVoteByUserIdAndDate() {
        Vote vote = voteService.getVoteByUserIdAndDate(UserTestData.USER_ID, LocalDate.of(2019, 9, 20));
        assertMatch(vote, VOTE_FROM_USER_FOR_TOKYO_20_09);
    }

    @Test
    void getAllUsersByRestaurantIdAndDateTime() {
        List<User> users = voteService.getAllUsersByRestaurantIdAndDate(TOKYO_CITY_ID, LocalDate.of(2019, 9, 20));
        UserTestData.assertMatch(users.get(0), UserTestData.USER);
    }
}

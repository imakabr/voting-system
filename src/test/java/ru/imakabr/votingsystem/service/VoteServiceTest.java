package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.imakabr.votingsystem.ItemTestData;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Vote;

import static ru.imakabr.votingsystem.RestaurantTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void get() throws Exception {

    }
}

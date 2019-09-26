package ru.imakabr.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.repository.UserRepository;
import ru.imakabr.votingsystem.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    private final VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(repository.save(vote), vote.getId());
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Vote> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public List<Restaurant> getAllRestaurantsByUserId(int UserId) {
        return repository.getAllRestaurantsByUserId(UserId);
    }

    public List<User> getAllUsersByRestaurantId(int RestId) {
        return repository.getAllUsersByRestaurantId(RestId);
    }

    public Restaurant getRestaurantByUserIdAndDateTime(int id, LocalDateTime dateTime) {
        return repository.getRestaurantByUserIdAndDateTime(id, dateTime);
    }

    List<User> getAllUsersByRestaurantIdAndDateTime(int RestId, LocalDateTime dateTime) {
        return repository.getAllUsersByRestaurantIdAndDateTime(RestId, dateTime);
    }

}

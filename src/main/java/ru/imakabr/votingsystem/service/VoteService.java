package ru.imakabr.votingsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.repository.UserRepository;
import ru.imakabr.votingsystem.repository.VoteRepository;

import javax.naming.TimeLimitExceededException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.*;

@Service
public class VoteService {
    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name", "email");

    private final VoteRepository voteRepository;

    private final UserRepository userRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    public Vote create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkTime();
        Vote vote = new Vote(null, userRepository.getOne(userId), restaurant, LocalDate.now());
        return voteRepository.save(vote);
    }

    public void update(Restaurant restaurant, int userId, int voteId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        LocalDate dateVote = get(voteId).getDate();
        checkDateTime(dateVote);
        Vote vote = new Vote(voteId, userRepository.getOne(userId), restaurant, dateVote);
        checkNotFoundWithId(voteRepository.save(vote), voteId);
    }

    public void delete(int voteId, int userId) {
        LocalDate dateVote = get(voteId).getDate();
        checkDateTime(dateVote);
        checkNotFoundWithId(voteRepository.delete(userId, dateVote) != 0, voteId);
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.findById(id).orElse(null), id);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll(SORT_NAME);
    }

    public List<Vote> getAllVotesByUserId(int id) {
        return voteRepository.getAllVotesByUserId(id);
    }

    public List<Restaurant> getAllRestaurantsByUserId(int UserId) {
        return voteRepository.getAllRestaurantsByUserId(UserId);
    }

    public List<User> getAllUsersByRestaurantId(int RestId) {
        return voteRepository.getAllUsersByRestaurantId(RestId);
    }

    public List<Vote> getAllVotesByRestaurantId(int RestId) {
        return voteRepository.getAllVotesByRestaurantId(RestId);
    }

    public Restaurant getRestaurantByUserIdAndDate(int userId, LocalDate date) {
        return voteRepository.getRestaurantByUserIdAndDate(userId, date);
    }

    public List<User> getAllUsersByRestaurantIdAndDate(int RestId, LocalDate date) {
        return voteRepository.getAllUsersByRestaurantIdAndDate(RestId, date);
    }

}

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

import java.time.LocalDate;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

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
        Vote vote = new Vote(null, userRepository.getOne(userId), restaurant, LocalDate.now());
        return voteRepository.save(vote);
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.save(vote), vote.getId());
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

    public Restaurant getRestaurantByUserIdAndDate(int userId, LocalDate date) {
        return voteRepository.getRestaurantByUserIdAndDate(userId, date);
    }

    List<User> getAllUsersByRestaurantIdAndDate(int RestId, LocalDate date) {
        return voteRepository.getAllUsersByRestaurantIdAndDate(RestId, date);
    }

}

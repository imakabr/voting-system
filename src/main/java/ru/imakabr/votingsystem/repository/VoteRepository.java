package ru.imakabr.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v.restaurant FROM Vote v WHERE v.user.id=?1")
    List<Restaurant> getAllRestaurantsByUserId(int id);

    @Query("SELECT v.user FROM Vote v WHERE v.restaurant.id=?1")
    List<User> getAllUsersByRestaurantId(int id);
}

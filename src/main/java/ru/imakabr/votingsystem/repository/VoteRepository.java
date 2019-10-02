package ru.imakabr.votingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.user.id=?1 and v.date=?2")
    int delete(int id, LocalDate date);

    @Query("SELECT v.restaurant FROM Vote v WHERE v.user.id=?1")
    List<Restaurant> getAllRestaurantsByUserId(int id);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.user.id=?1")
    List<Vote> getAllVotesByUserId(int id);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT  v FROM Vote v WHERE v.restaurant.id=?1")
    List<Vote> getAllVotesByRestaurantId(int id);

    @Query("SELECT v.restaurant FROM Vote v WHERE v.user.id=?1 and v.date=?2")
    Restaurant getRestaurantByUserIdAndDate(int id, LocalDate date);

    @Query("SELECT DISTINCT v.user FROM Vote v WHERE v.restaurant.id=?1")
    List<User> getAllUsersByRestaurantId(int id);

    @Query("SELECT v.user FROM Vote v WHERE v.restaurant.id=?1 and v.date=?2")
    List<User> getAllUsersByRestaurantIdAndDate(int id, LocalDate date);
}

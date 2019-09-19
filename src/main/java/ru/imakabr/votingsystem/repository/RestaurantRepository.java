package ru.imakabr.votingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Restaurant u WHERE u.id=:id")
//    int delete(@Param("id") int id);

    //    https://stackoverflow.com/a/46013654/548473
//    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT u FROM Restaurant u WHERE u.id=?1")
//    User getWithMenus(int id);
}

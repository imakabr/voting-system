//package ru.imakabr.votingsystem.repository;
//
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//import ru.imakabr.votingsystem.model.RestaurantTest;
//
//@Transactional(readOnly = true)
//public interface RestaurantTestRepository extends JpaRepository<RestaurantTest, Integer> {
//
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM RestaurantTest r WHERE r.id=:id")
//    int delete(@Param("id") int id);
//
////    https://stackoverflow.com/a/46013654/548473
////    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
////    @Query("SELECT u FROM Restaurant u WHERE u.id=?1")
////    User getWithMenus(int id);
//}

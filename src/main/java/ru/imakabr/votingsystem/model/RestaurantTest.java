//package ru.imakabr.votingsystem.model;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
//@NamedQueries(
//        @NamedQuery(name = RestaurantTest.DELETE, query = "DELETE FROM RestaurantTest r WHERE r.id=:id")
//)
//
//@Entity
//@Table(name = "restaurants_test")
//public class RestaurantTest extends AbstractNamedEntity {
//
//    public static final String DELETE = "RestaurantTest.delete";
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "items_test", joinColumns= @JoinColumn(name="rest_id"))
//    @OrderBy("date_time, name asc")
//    protected List<ItemTest> items;
//
//    public void setItems(List<ItemTest> items) {
//        this.items = items;
//    }
//
//    public RestaurantTest() {
//    }
//
//    public RestaurantTest(int id, String name) {
//        super(id, name);
//    }
//
//    public RestaurantTest(int id, String name, List<ItemTest> items) {
//        super(id, name);
//        this.items = items;
//    }
//}

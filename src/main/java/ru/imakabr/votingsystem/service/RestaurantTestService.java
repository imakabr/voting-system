//package ru.imakabr.votingsystem.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import ru.imakabr.votingsystem.model.RestaurantTest;
//import ru.imakabr.votingsystem.repository.RestaurantTestRepository;
//
//import java.util.List;
//
//import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;
//
//@Service
//public class RestaurantTestService {
//
//    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");
//
//    private final RestaurantTestRepository repository;
//
//    @Autowired
//    public RestaurantTestService(RestaurantTestRepository repository) {
//        this.repository = repository;
//    }
//
//    public RestaurantTest create(RestaurantTest restaurant) {
//        Assert.notNull(restaurant, "restaurant must not be null");
//        return repository.save(restaurant);
//    }
//
//    public void delete(int id) {
//        checkNotFoundWithId(repository.delete(id) != 0, id);
//    }
//
//    public RestaurantTest get(int id) {
//        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
//    }
//
//    public List<RestaurantTest> getAll() {
//        return repository.findAll(SORT_NAME);
//    }
//
//    public void update(RestaurantTest restaurant) {
//        Assert.notNull(restaurant, "restaurant must not be null");
//        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
//    }
//}

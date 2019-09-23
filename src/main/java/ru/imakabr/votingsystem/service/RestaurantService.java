package ru.imakabr.votingsystem.service;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

//    public void delete(int id) {
//        checkNotFoundWithId(repository.delete(id) != 0, id);
//    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public Restaurant getByDate(int id, LocalDateTime date) {
        Filter filter = entityManager.unwrap(Session.class).enableFilter("filterByDate");
        filter.setParameter("date_time", date);
        Restaurant restaurant = checkNotFoundWithId(repository.findById(id).orElse(null), id);
        entityManager.unwrap(Session.class).disableFilter("filterByDate");
        return restaurant;
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    @Transactional
    public List<Restaurant> getAllByDate(LocalDateTime date) {
        Filter filter = entityManager.unwrap(Session.class).enableFilter("filterByDate");
        filter.setParameter("date_time", date);
        List<Restaurant> restaurants = repository.findAll();
        entityManager.unwrap(Session.class).disableFilter("filterByDate");
        return restaurants;
    }

//    public User getWithMenus(int id) {
//        return checkNotFoundWithId(repository.getWithMenus(id), id);
//    }
}

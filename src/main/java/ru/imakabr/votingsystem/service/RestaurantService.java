package ru.imakabr.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    @Cacheable("allRestaurantsWithMealsByDate")
    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        return repository.getAllWithMenuByDate(date);
    }

    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(repository.getWithMenu(id), id);
    }

    @Cacheable("oneRestaurantWithMealsByDate")
    public Restaurant getWithMenuByDate(int id, LocalDate date) {
        return checkNotFoundWithId(repository.getWithMenuByDate(id, date), id);
    }

}

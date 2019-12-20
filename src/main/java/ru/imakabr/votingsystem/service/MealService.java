package ru.imakabr.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.imakabr.votingsystem.model.Meal;
import ru.imakabr.votingsystem.repository.MealRepository;
import ru.imakabr.votingsystem.repository.RestaurantRepository;
import ru.imakabr.votingsystem.util.ValidationUtil;

import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository mealRepository;

    private RestaurantRepository restaurantRepository;

    @Autowired
    public MealService(MealRepository repository, RestaurantRepository restaurantRepository) {
        this.mealRepository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = {"allRestaurantsWithMealsByDate", "oneRestaurantWithMealsByDate"}, allEntries = true)
    public Meal create(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        meal.setRestaurant(restaurantRepository.getOne(restaurantId));
        return mealRepository.save(meal);
    }

    @CacheEvict(value = {"allRestaurantsWithMealsByDate", "oneRestaurantWithMealsByDate"}, allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(mealRepository.delete(id) != 0, id);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(mealRepository.findById(id).orElse(null), id);
    }

    public List<Meal> getAll() {
        return mealRepository.findAll();
    }

    @CacheEvict(value = {"allRestaurantsWithMealsByDate", "oneRestaurantWithMealsByDate"}, allEntries = true)
    public void update(Meal meal, int restaurantId, int mealId) {
        Assert.notNull(meal, "meal must not be null");
        ValidationUtil.assureMealIdConsistent(meal, mealId);
        meal.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(mealRepository.save(meal), meal.getId());
    }

}

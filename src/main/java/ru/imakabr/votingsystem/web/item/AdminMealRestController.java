package ru.imakabr.votingsystem.web.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.imakabr.votingsystem.model.Meal;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.service.MealService;
import ru.imakabr.votingsystem.service.RestaurantService;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = AdminMealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealRestController {

    public static final String REST_URL = "/rest/admin/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService mealService;

    @Autowired
    protected RestaurantService restaurantService;

    @GetMapping("meals/{id}")
    public Meal get(@PathVariable int id) {
        return mealService.get(id);
    }

    @PostMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal, @PathVariable int restaurantId) {
        log.info("create {}", meal);
        Meal created = mealService.create(meal, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {}", meal);
        mealService.update(meal, restaurantId, id);
    }

    @DeleteMapping("meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete meal = " + id);
        mealService.delete(id);
    }

    @GetMapping("/{id}/meals")
    public Restaurant getAllByRestaurantId(@PathVariable int id) {
        return restaurantService.getWithMenu(id);
    }

    @GetMapping("/{id}/meals/filter")
    public Restaurant getAllByRestaurantIdAndDate(@PathVariable int id, @RequestParam(required = false) LocalDate date) {
        return restaurantService.getWithMenuByDate(id, date);
    }

}
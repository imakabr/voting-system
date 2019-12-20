package ru.imakabr.votingsystem.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    public static final String REST_URL = "/rest/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getList");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}/today")
    public Restaurant getRestaurantWithMenuToday(@PathVariable int id) {
        LocalDate date = LocalDate.now();
        return restaurantService.getWithMenuByDate(id, date);
    }

    @GetMapping("/{id}/by")
    public Restaurant getRestaurantWithMenuByDate(@PathVariable int id, @RequestParam(required = false) LocalDate date) {
        return restaurantService.getWithMenuByDate(id, date);
    }

    @GetMapping("/today")
    public List<Restaurant> getAllTodayWithMenu() {
        log.info("getAll");
        LocalDate date = LocalDate.now();
        return restaurantService.getAllWithMenuByDate(date);
    }

    @GetMapping("/by")
    public List<Restaurant> getAllByDate(@RequestParam(required = false) LocalDate date) {
        log.info("getAllByDate/filter");
        return restaurantService.getAllWithMenuByDate(date);
    }

}


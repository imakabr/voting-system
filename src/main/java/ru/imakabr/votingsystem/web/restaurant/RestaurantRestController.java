package ru.imakabr.votingsystem.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.service.RestaurantService;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.to.RestaurantTO;
import ru.imakabr.votingsystem.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    public static final String REST_URL = "/rest/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected VoteService voteService;

    @GetMapping("/list")
    public List<Restaurant> getAll() {
        log.info("getList");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}/today")
    public Restaurant getAllByRestaurantIdToday(@PathVariable int id) {
        LocalDate date = LocalDate.now();
        return restaurantService.getWithItemsByDate(id, date);
    }

    @GetMapping("/{id}/filter")
    public Restaurant getAllByRestaurantIdAndDate(@PathVariable int id, @RequestParam(required = false) LocalDate date) {
        return restaurantService.getWithItemsByDate(id, date);
    }

    @GetMapping("/today")
    public RestaurantTO getAllToday() {
        log.info("getAll");
        LocalDate date = LocalDate.now();
        Restaurant restaurant = voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
        List<Restaurant> restaurants = restaurantService.getAllWithItemsByDate(date);
        return new RestaurantTO(restaurants, restaurant);
    }

    @GetMapping("/filter")
    public RestaurantTO getAllByDate(@RequestParam(required = false) LocalDate date) {
        log.info("getAllByDate/filter");
        Restaurant restaurant = voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
        List<Restaurant> restaurants = restaurantService.getAllWithItemsByDate(date);
        return new RestaurantTO(restaurants, restaurant);
    }

    @GetMapping("/vote/today")
    public Restaurant getVoteToday() {
        LocalDate date = LocalDate.now();
        return voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
    }

    @GetMapping("/vote/filter")
    public Restaurant getVoteByDate(@RequestParam(required = false) LocalDate date) {
        return voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
    }

}


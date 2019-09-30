package ru.imakabr.votingsystem.web.restaurant;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.service.RestaurantService;
import ru.imakabr.votingsystem.service.UserService;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.to.RestaurantTO;
import ru.imakabr.votingsystem.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @GetMapping
    public RestaurantTO getAll() {
        log.info("getAll");
        LocalDate date = LocalDate.of(2019, 9, 20);
        Restaurant restaurant = voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
        List<Restaurant> restaurants = restaurantService.getAllWithItemsByDate(date);
        return new RestaurantTO(restaurants, restaurant.getName());
    }

    @GetMapping("/filter")
    public RestaurantTO getOnDate(@RequestParam(required = false) LocalDate date) {
        log.info("getOnDate/filter");
        Restaurant restaurant = voteService.getRestaurantByUserIdAndDate(SecurityUtil.authUserId(), date);
        List<Restaurant> restaurants = restaurantService.getAllWithItemsByDate(date);
        return new RestaurantTO(restaurants, restaurant.getName());
    }


}


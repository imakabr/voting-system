package ru.imakabr.votingsystem.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.service.RestaurantService;
import ru.imakabr.votingsystem.service.VoteService;

import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController {

    public static final String REST_URL = "/rest/admin/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected VoteService voteService;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public List<User> get(@PathVariable int id) {
        return voteService.getAllUsersByRestaurantId(id);
    }


}

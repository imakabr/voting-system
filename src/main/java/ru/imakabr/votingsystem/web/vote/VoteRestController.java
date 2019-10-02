package ru.imakabr.votingsystem.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.imakabr.votingsystem.model.Restaurant;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.web.SecurityUtil;

import java.net.URI;
import java.util.List;

import static ru.imakabr.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    public static final String REST_URL = "/rest/votes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VoteService voteService;

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return voteService.getAllVotesByUserId(SecurityUtil.authUserId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update vote");
        voteService.update(restaurant, SecurityUtil.authUserId(), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@RequestBody Restaurant restaurant) {
        log.info("create vote");
        Vote created = voteService.create(restaurant, SecurityUtil.authUserId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote");
        voteService.delete(id, SecurityUtil.authUserId());
    }
}

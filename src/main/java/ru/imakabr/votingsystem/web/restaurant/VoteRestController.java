package ru.imakabr.votingsystem.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imakabr.votingsystem.model.Vote;
import ru.imakabr.votingsystem.service.VoteService;
import ru.imakabr.votingsystem.web.SecurityUtil;

import java.util.List;

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
}

package org.konstr.votingsystem.web.vote;

import org.konstr.votingsystem.AuthorizedUser;
import org.konstr.votingsystem.model.VoteResult;
import org.konstr.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 23-Feb-17.
 */
@RestController
@RequestMapping("/v1/votes")
public class VoteController {
    private static final Logger LOG = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getCurrentResults() {
        LOG.info("getCurrentResults");
        return service.getCurrentResults();
    }

    @GetMapping(value = "/with", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultsByDate(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        LOG.info("getResultsByDate " + date);
        return service.getResultsByDate(date);
    }

    @PutMapping("/restaurants/{restaurantId}")
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        LOG.info("voting for restaurant " + restaurantId);
        service.vote(restaurantId, AuthorizedUser.id());
    }
}

package org.konstr.votingsystem.web.restaurant;

import org.konstr.votingsystem.AuthorizedUser;
import org.konstr.votingsystem.service.RestaurantService;
import org.konstr.votingsystem.to.RestaurantTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for user " + userId);
        return service.getAll(userId);
    }
}

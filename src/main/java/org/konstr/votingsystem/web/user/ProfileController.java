package org.konstr.votingsystem.web.user;

import org.konstr.votingsystem.AuthorizedUser;
import org.konstr.votingsystem.service.UserService;
import org.konstr.votingsystem.to.UserTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.konstr.votingsystem.util.ValidationUtil.checkIdConsistent;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@RestController
@RequestMapping(ProfileController.URL)
public class ProfileController {
    static final String URL = "/v1/profile";
    private static final Logger LOG = getLogger(ProfileController.class);

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo getTo() {
        int userId = AuthorizedUser.id();
        LOG.info("getTo " + userId);
        return service.getTo(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo) {
        LOG.info("update " + userTo);
        checkIdConsistent(userTo, AuthorizedUser.id());
        service.update(userTo);
    }

    @DeleteMapping
    public void delete() {
        int userId = AuthorizedUser.id();
        LOG.info("delete " + userId);
        service.delete(userId);
    }
}

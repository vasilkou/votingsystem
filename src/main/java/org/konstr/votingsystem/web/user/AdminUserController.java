package org.konstr.votingsystem.web.user;

import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.checkIdConsistent;
import static org.konstr.votingsystem.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
@RestController
@RequestMapping(AdminUserController.URL)
public class AdminUserController {
    static final String URL = "/v1/admin/users";
    private static final Logger LOG = getLogger(AdminUserController.class);

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    @GetMapping(value = "/with", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByMail(@RequestParam("email") String email) {
        LOG.info("getByEmail " + email);
        return service.getByEmail(email);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        checkNew(user);
        LOG.info("create " + user);
        User created = service.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody User user, @PathVariable("id") int id) {
        LOG.info("update " + user);
        checkIdConsistent(user, id);
        service.update(user);
    }

    @PatchMapping("/{id}")
    public void enable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        LOG.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }
}

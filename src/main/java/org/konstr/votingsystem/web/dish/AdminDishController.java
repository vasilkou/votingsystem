package org.konstr.votingsystem.web.dish;

import org.konstr.votingsystem.model.Dish;
import org.konstr.votingsystem.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.konstr.votingsystem.util.ValidationUtil.*;

/**
 * Created by Yury Vasilkou
 * Date: 23-Feb-17.
 */
@RestController
@RequestMapping(AdminDishController.URL)
public class AdminDishController {
    static final String URL = "/v1/admin/restaurants";
    private static final Logger LOG = LoggerFactory.getLogger(AdminDishController.class);

    @Autowired
    private DishService service;

    @GetMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenu(@PathVariable("restaurantId") int restaurantId) {
        LOG.info("getMenu, restaurant " + restaurantId);
        return service.getMenu(restaurantId);
    }

    @GetMapping(value = "/{restaurantId}/dishes/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        LOG.info("get " + id + ", restaurant " + restaurantId);
        return service.get(id, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/dishes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish,
                                                   @PathVariable("restaurantId") int restaurantId) {
        checkNew(dish);
        LOG.info("create " + dish + ", restaurant " + restaurantId);
        Dish created = service.save(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{restaurantId}/dishes/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{restaurantId}/menu",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dish>> createMenu(@Valid @RequestBody List<Dish> menu,
                                           @PathVariable("restaurantId") int restaurantId) {
        checkAllNew(menu);
        LOG.info("create " + menu + ", restaurant " + restaurantId);
        List<Dish> created = service.saveMenu(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{restaurantId}/dishes")
                .buildAndExpand(restaurantId).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Dish dish,
                       @PathVariable("restaurantId") int restaurantId,
                       @PathVariable("id") int id) {
        LOG.info("update " + dish + ", restaurant " + restaurantId);
        checkIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes")
    public void updateMenu(@Valid @RequestBody List<Dish> menu, @PathVariable("restaurantId") int restaurantId) {
        LOG.info("update " + menu + ", restaurant " + restaurantId);
        service.updateMenu(menu, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    public void delete(@PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        LOG.info("delete " + id + ", restaurant " + restaurantId);
        service.delete(id, restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes")
    public void deleteMenu(@PathVariable("restaurantId") int restaurantId) {
        LOG.info("deleteMenu, restaurant " + restaurantId);
        service.deleteMenu(restaurantId);
    }
}

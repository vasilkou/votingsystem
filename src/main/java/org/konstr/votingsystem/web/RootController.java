package org.konstr.votingsystem.web;

import org.konstr.votingsystem.service.UserService;
import org.konstr.votingsystem.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.konstr.votingsystem.util.UserUtil.createNewFromTo;
import static org.konstr.votingsystem.util.ValidationUtil.checkNew;

/**
 * Created by Yury Vasilkou
 * Date: 14-Mar-17.
 */
@RestController
@RequestMapping("/v1/register")
public class RootController {
    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(@Valid @RequestBody UserTo userTo) {
        checkNew(userTo);
        LOG.info("register " + userTo);
        service.save(createNewFromTo(userTo));
    }
}

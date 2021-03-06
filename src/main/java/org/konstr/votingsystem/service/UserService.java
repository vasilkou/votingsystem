package org.konstr.votingsystem.service;

import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.to.UserTo;
import org.konstr.votingsystem.util.exceptions.NotFoundException;

import java.util.List;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
public interface UserService {
    User save(User user);

    List<User> getAll();

    User get(int id) throws NotFoundException;

    UserTo getTo(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo userTo);

    void delete(int id) throws NotFoundException;

    void enable(int id, boolean enabled);

    void evictCache();
}

package org.konstr.votingsystem.service;

import org.konstr.votingsystem.AuthorizedUser;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.repository.UserRepository;
import org.konstr.votingsystem.to.UserTo;
import org.konstr.votingsystem.util.UserUtil;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static org.konstr.votingsystem.util.UserUtil.prepareToSave;
import static org.konstr.votingsystem.util.ValidationUtil.checkNotFound;
import static org.konstr.votingsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public UserTo getTo(int id) throws NotFoundException {
        return UserUtil.asTo(get(id));
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.findByEmail(email), "email=" + email);
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(prepareToSave(user));
    }

    @Transactional
    @Override
    public void update(UserTo userTo) {
        Assert.notNull(userTo, "user must not be null");
        User user = UserUtil.updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    @Override
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.findByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }
}

package org.konstr.votingsystem.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konstr.votingsystem.model.Role;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.to.UserTo;
import org.konstr.votingsystem.util.UserUtil;
import org.konstr.votingsystem.util.ValidationUtil;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.konstr.votingsystem.UserTestData.*;

/**
 * Created by Yury Vasilkou
 * Date: 28-Feb-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetTo() throws Exception {
        UserTo userTo = UserUtil.asTo(service.get(ADMIN_ID));
        MATCHER_TO.assertEquals(UserUtil.asTo(ADMIN), userTo);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByEmailNotFound() throws Exception {
        service.getByEmail("wrong@email.com");
    }

    @Test
    public void getAll() throws Exception {
        Collection<User> all = service.getAll();
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER), all);
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate Email", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("Новое имя");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }

    @Test
    public void testUpdateTo() throws Exception {
        UserTo updated = UserUtil.asTo(new User(USER));
        updated.setName("Новое имя");
        updated.setEmail("updated@gmail.com");
        service.update(updated);
        MATCHER_TO.assertEquals(updated, UserUtil.asTo(service.get(USER_ID)));
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailUpdate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("admin@gmail.com");
        service.update(updated);
    }

    @Test
    public void testSetEnabledEquals() {
        service.enable(USER_ID, false);
        Assert.assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        Assert.assertTrue(service.get(USER_ID).isEnabled());
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new User(null, "  ", "invalid@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "not-email", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.update(new User(USER_ID, "  ", "invalid@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.update(new User(USER_ID, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.update(new User(USER_ID, "User", "invalid@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.update(new User(USER_ID, "User", "invalid@yandex.ru", "123", Role.ROLE_USER)), ConstraintViolationException.class);
    }

    public static <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(ValidationUtil.getRootCause(e), instanceOf(exceptionClass));
        }
    }
}

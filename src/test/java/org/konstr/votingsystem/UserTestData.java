package org.konstr.votingsystem;

import org.konstr.votingsystem.matcher.ModelMatcher;
import org.konstr.votingsystem.model.Role;
import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.to.UserTo;

import java.util.Objects;

import static org.konstr.votingsystem.model.BaseEntity.START_SEQ;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    public static final ModelMatcher<UserTo> MATCHER_TO = ModelMatcher.of(UserTo.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                    )
    );
}

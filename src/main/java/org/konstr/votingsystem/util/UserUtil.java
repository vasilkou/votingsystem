package org.konstr.votingsystem.util;

import org.konstr.votingsystem.model.User;
import org.konstr.votingsystem.to.UserTo;

/**
 * Created by Yury Vasilkou
 * Date: 02-Mar-17.
 */
public class UserUtil {
/*
    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), Role.ROLE_USER);
    }
*/

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}

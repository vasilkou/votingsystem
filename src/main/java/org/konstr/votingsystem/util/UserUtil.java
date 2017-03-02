package org.konstr.votingsystem.util;

import org.konstr.votingsystem.model.User;

/**
 * Created by Yury Vasilkou
 * Date: 02-Mar-17.
 */
public class UserUtil {
    public static User prepareToSave(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}

package org.konstr.votingsystem;

import org.konstr.votingsystem.model.BaseEntity;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public class AuthorizedUser {
    public static int id = BaseEntity.START_SEQ;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }
}

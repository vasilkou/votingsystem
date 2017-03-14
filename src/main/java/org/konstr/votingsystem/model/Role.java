package org.konstr.votingsystem.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Yury Vasilkou
 * Date: 22-Feb-17.
 */
public enum Role  implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

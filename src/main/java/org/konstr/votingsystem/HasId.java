package org.konstr.votingsystem;

/**
 * Created by Yury Vasilkou
 * Date: 09-Mar-17.
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return (getId() == null);
    }
}

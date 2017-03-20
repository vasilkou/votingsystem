package org.konstr.votingsystem.util;

import org.konstr.votingsystem.HasId;
import org.konstr.votingsystem.util.exceptions.NotFoundException;

/**
 * Created by Yury Vasilkou
 * Date: 01-Mar-17.
 */
public class ValidationUtil {

    public static final String NOT_FOUND_KEY = "exception.entity.not_found";
    public static final String MUST_BE_NEW_KEY = "exception.entity.must_be_new";
    public static final String WRONG_ID_KEY = "exception.entity.must_be_with_id";

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException(NOT_FOUND_KEY + " " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " " + MUST_BE_NEW_KEY);
        }
    }

    public static void checkIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " " + WRONG_ID_KEY + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}

package org.konstr.votingsystem.util.exceptions;

/**
 * Created by Yury Vasilkou
 * Date: 27-Feb-17.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

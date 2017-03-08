package org.konstr.votingsystem.util.exceptions;

/**
 * Created by Yury Vasilkou
 * Date: 08-Mar-17.
 */
public class VoteForbiddenException extends RuntimeException {
    public VoteForbiddenException(String message) {
        super(message);
    }
}

package org.konstr.votingsystem.web;

import org.konstr.votingsystem.util.ValidationUtil;
import org.konstr.votingsystem.util.exceptions.ErrorInfo;
import org.konstr.votingsystem.util.exceptions.NotFoundException;
import org.konstr.votingsystem.util.exceptions.VoteForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.konstr.votingsystem.util.ValidationUtil.*;
import static org.konstr.votingsystem.util.VoteUtil.VOTE_FORBIDDEN_KEY;

/**
 * Created by Yury Vasilkou
 * Date: 16-Mar-17.
 */
@ControllerAdvice
public class ExceptionInfoHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private static Map<String, String> constraintCodeMap = new HashMap<String, String>() {
        {
            put("users_unique_email_idx", "exception.users.duplicate_email");
            put("name_address_idx", "exception.restaurants.duplicate");
        }
    };

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleNotFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, new NotFoundException(localizeErrorMessage(e, NOT_FOUND_KEY)), false);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, IllegalArgumentException e) {
        String key;

        if (e.getMessage().contains(MUST_BE_NEW_KEY)) {
            key = MUST_BE_NEW_KEY;
        } else if (e.getMessage().contains(WRONG_ID_KEY)) {
            key = WRONG_ID_KEY;
        } else {
            return logAndGetErrorInfo(req, e, false);
        }

        return logAndGetErrorInfo(req, new IllegalArgumentException(localizeErrorMessage(e, key)), false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            Optional<Map.Entry<String, String>> entry = constraintCodeMap.entrySet().stream()
                    .filter((it) -> rootMsg.toLowerCase().contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                return logAndGetErrorInfo(req,
                        new DataIntegrityViolationException(
                                messageSource.getMessage(entry.get().getValue(), null, LocaleContextHolder.getLocale())
                        ), false);
            }
        }
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo bindValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetValidationErrorInfo(req, e.getBindingResult());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(VoteForbiddenException.class)
    @ResponseBody
    public ErrorInfo handleVoteError(HttpServletRequest req, VoteForbiddenException e) {
        return logAndGetErrorInfo(req, new VoteForbiddenException(localizeErrorMessage(e, VOTE_FORBIDDEN_KEY)), false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    public String localizeErrorMessage(Throwable e, String key) {
        return StringUtils.replace(
                e.getMessage(),
                key,
                messageSource.getMessage(key, null, LocaleContextHolder.getLocale())
        );
    }

    public static ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, BindingResult result) {
        String[] details = result.getFieldErrors().stream()
                .map(fe -> fe.getField() + ' ' + fe.getDefaultMessage())
                .toArray(String[]::new);

        LOG.warn("Validation exception at request " + req.getRequestURL() + ": " + Arrays.toString(details));
        return new ErrorInfo(req.getRequestURL(), "ValidationException", details);
    }

    public static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), e);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + e.toString());
        }
        return new ErrorInfo(req.getRequestURL(), e);
    }
}

package ru.imakabr.votingsystem.util;


import ru.imakabr.votingsystem.model.AbstractBaseEntity;
import ru.imakabr.votingsystem.util.exception.NotFoundException;
import ru.imakabr.votingsystem.util.exception.TimeVoteLimitException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkTime(LocalDate dateVote) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalTime timeRestriction = LocalTime.of(17, 00);
        if (!today.isEqual(dateVote)) {
            throw new TimeVoteLimitException("You have already voted on " + dateVote + ". You can not revote today " + today);
        }
        if (timeRestriction.isBefore(now)) {
            throw new TimeVoteLimitException("Time is " + now + " You can not vote after 12:00");
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}

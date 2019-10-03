package ru.imakabr.votingsystem.util;


import ru.imakabr.votingsystem.model.AbstractBaseEntity;
import ru.imakabr.votingsystem.model.Item;
import ru.imakabr.votingsystem.util.exception.NotFoundException;
import ru.imakabr.votingsystem.util.exception.TimeVoteLimitException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static LocalTime timeRestriction = LocalTime.of(23, 59);

    public static void checkDateTime(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (!today.isEqual(date)) {
            throw new TimeVoteLimitException("You voted on " + date + ". You can not change it today " + today);
        }
        checkTime();
    }

    public static void checkTime() {
        LocalTime now = LocalTime.now();
        if (timeRestriction.isBefore(now)) {
            throw new TimeVoteLimitException("Time is " + now + " You can not vote after 11:59");
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

    public static void assureItemIdConsistent(Item item, int id) {
        if (item.isNew()) {
            item.setId(id);
        } else if (item.getId() != id) {
            throw new IllegalArgumentException(item + " must be with id=" + id);
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

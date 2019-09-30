package ru.imakabr.votingsystem.web.format;

import org.springframework.format.Formatter;
import ru.imakabr.votingsystem.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalTime(text);
    }

    @Override
    public String print(LocalTime a, Locale l) {
        return a.toString();
    }
}

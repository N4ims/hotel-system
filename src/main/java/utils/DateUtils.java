package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.Date;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.valueOf(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.valueOf(localDateTime.atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
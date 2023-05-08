package com.n4ims.hotelsystem.utils;

import java.time.LocalDate;;
import java.sql.Date;

public class DateUtils {

    public static java.sql.Date asDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * gkislin
 * 20.07.2016
 */
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "н.в." : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String date) {
        if (HtmlHelper.isEmpty(date) || "н.в.".equals(date)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy"));
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Period;

public class HtmlHelper {
    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Period period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate());
    }
}

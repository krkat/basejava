package ru.javawebinar.basejava.util;

public class HtmlHelper {
    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
